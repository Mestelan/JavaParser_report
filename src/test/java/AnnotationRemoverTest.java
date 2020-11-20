import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AnnotationRemoverTest {

    @Test
    public void modifyClassByRemovingCauseOrWholeAnnotation() throws IOException {
        String testRoot = "src/main/java/MyClass.java";
        Path path = Paths.get(testRoot);

        AnnotationRemover remover = new AnnotationRemover();
        assertElementIsRemoved(false, remover, path);
        assertElementIsRemoved(true, remover, path);
    }

    private void assertElementIsRemoved(boolean lexical, AnnotationRemover remover, Path paths) throws IOException {
        String s = remover.deleteFromAnnotation(lexical, paths);
        Assert.assertFalse(s.contains("bad"));
    }
}