import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class BuildSrcPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("BuildSrcPlugin apply " + target);
    }
}
