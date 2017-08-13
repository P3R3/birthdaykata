package birthdaykata.infrastructure.flatFile.domain.repositories;

import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.RepositoryNotAvailableException;
import birthdaykata.infrastructure.flatFile.domain.models.FlatFileUserFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class FlatFileRepositoryTest {

    @Mock
    private File file;


    @Test
    public void when_file_not_exists_then_error() {
        //stub not exitst
        Mockito.when(file.exists()).thenReturn(false);

        try {
            //test
            new FlatFileRepository(file);
            Assertions.fail("exception must be thrown");
        }catch (RepositoryNotAvailableException e) {
            //validate
            Assertions.assertThat(e.getMessage()).isEqualTo("Repo not available");
        }

        //verify
        Mockito.verify(file).exists();
    }
    @Test
    public void when_file_can_not_read_then_error() {
        //stub cannot read
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(file.canRead()).thenReturn(false);

        try {
            //test
            new FlatFileRepository(file);
            Assertions.fail("exception must be thrown");
        }catch (RepositoryNotAvailableException e) {
            //validate
            Assertions.assertThat(e.getMessage()).isEqualTo("Repo not available");
        }

        //verify
        Mockito.verify(file).exists();
        Mockito.verify(file).canRead();
    }
    @Test
    public void when_file_is_a_directory_then_error() {
        //stub is not a file
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(file.canRead()).thenReturn(true);
        Mockito.when(file.isFile()).thenReturn(false);

        try {
            //test
            new FlatFileRepository(file);
            Assertions.fail("exception must be thrown");
        }catch (RepositoryNotAvailableException e) {
            //validate
            Assertions.assertThat(e.getMessage()).isEqualTo("Repo not available");
        }

        //verify
        Mockito.verify(file).exists();
        Mockito.verify(file).canRead();
        Mockito.verify(file).isFile();
    }

}
