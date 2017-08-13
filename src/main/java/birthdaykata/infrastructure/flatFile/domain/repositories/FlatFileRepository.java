package birthdaykata.infrastructure.flatFile.domain.repositories;


import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.RepositoryNotAvailableException;
import birthdaykata.domain.repositories.UserRepository;
import birthdaykata.infrastructure.flatFile.domain.models.FlatFileUserFactory;
import com.lambdista.util.Try;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlatFileRepository implements UserRepository {

    public static final String FILE_SEPARATOR = ",";
    private final Path path;
    private final FlatFileUserFactory userFactory;

    public FlatFileRepository(String filePath) {
        this(new File(filePath));
    }

    FlatFileRepository(File file) {

        Try<Path> pathTry = new Try.Success<>(file)
                .filter(File::exists)
                .filter(File::canRead)
                .filter(File::isFile)
                .map(File::toPath);

        if (pathTry.isFailure()) {
            throw pathTry.failed().map(error -> new RepositoryNotAvailableException("Repo not available", error)).get();
        }

        this.path = pathTry.get();
        this.userFactory = new FlatFileUserFactory();
    }

    @Override
    public List<User> getAll()  {
        return readAllLines().stream()
                .filter(byNotEmpty())
                .filter(byNotHeader())
                .map(line -> line.split(FILE_SEPARATOR))
                .map(userFactory::fromRecord)
                .collect(Collectors.toList());

    }

    private Predicate<String> byNotEmpty() {
        return s -> !s.isEmpty();
    }


    private Predicate<String> byNotHeader() {
        return line->!line.trim().startsWith("#");
    }

    private List<String> readAllLines() {
        try {
            return Files.readAllLines(this.path);
        } catch (IOException e) {
            throw new RepositoryNotAvailableException("cannot read file", e);
        }
    }


}
