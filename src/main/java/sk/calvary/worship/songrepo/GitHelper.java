package sk.calvary.worship.songrepo;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

/**
 * @author marcinMarcin
 */
public class GitHelper {

    private static final String LOCAL_PATHNAME = "songs/songbookUCO";
    private static final String UCO_SONG_REPO_URI = "https://github.com/alamilar/rivendell-songs-repo.git";

    private static void cloneRepo() throws IOException, GitAPIException {
        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI(UCO_SONG_REPO_URI);
        File f‌ile = new File(LOCAL_PATHNAME);
        cloneCommand.setDirectory(f‌ile);
        cloneCommand.call();
        System.out.println("Cloning repository finished");
    }

    public static void cloneSongRepository() throws GitAPIException, IOException {
        File file = new File(LOCAL_PATHNAME);

        if (file.isDirectory()) {
            if (file.list().length > 0) {
                System.out.println("Directory is not empty!");
            } else {
                System.out.println("Directory is empty!");
                cloneRepo();
            }
        } else {
            System.out.println("This is not a directory");
            f‌ile.mkdir();
            cloneRepo();
        }
    }

    public static void main(String[] args) throws GitAPIException, IOException {
        cloneSongRepository();
    }
}
