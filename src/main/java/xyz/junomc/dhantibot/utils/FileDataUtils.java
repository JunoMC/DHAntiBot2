package xyz.junomc.dhantibot.utils;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileDataUtils {
    private AntiBotUtils antiBotUtils;

    public FileDataUtils() {
        this.antiBotUtils = new AntiBotUtils();
    }

    public void WriteFile(File f, String... strings) {
        Arrays.asList(strings).forEach(str -> {
            FileWriter fr;
            try {
                fr = new FileWriter(f, true);
                BufferedWriter br = new BufferedWriter(fr);

                br.write(str);
                br.newLine();

                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void WriteNew(File f, String... strings) {
        try {
            FileWriter myWriter = new FileWriter(f);
            myWriter.write(String.join(this.antiBotUtils.w(10), strings));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteFile(File f, List<String> strings) {
        strings.forEach(str -> {
            FileWriter fr;
            try {
                fr = new FileWriter(f, true);
                BufferedWriter br = new BufferedWriter(fr);

                br.write(str);
                br.newLine();

                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void WriteNew(File f, List<String> strings) {
        try {
            FileWriter myWriter = new FileWriter(f);
            myWriter.write(String.join(this.antiBotUtils.w(10), strings));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteFile(String path, String... strings) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);

        WriteFile(f, strings);
    }

    public void WriteNew(String path, List<String> strings) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);

        WriteNew(f, strings);
    }

    public void WriteFile(String path, List<String> strings) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);

        WriteFile(f, strings);
    }

    public void createFile(File f, String... data) {
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                WriteFile(f, data);
            }
        }
    }

    public void createFile(File f, List<String> data) {
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                WriteFile(f, data);
            }
        }
    }

    public Path getPath(String path) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);

        return f.toPath();
    }

    public List<String> readLines(String path) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());

        try {
            list = Files.readAllLines(getPath(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void createFile(String path, String... data) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);

        createFile(f, data);
    }

    public void createFile(String path, List<String> data) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);

        createFile(f, data);
    }

    public YamlMapping read(File f) {
        YamlMapping mapping = null;
        try {
            mapping = Yaml.createYamlInput(f).readYamlMapping();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapping;
    }

    public YamlMapping read(String path) {
        String canonicalPath = null;
        try {
            canonicalPath = new File(this.antiBotUtils.w(46)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = new File(canonicalPath, this.antiBotUtils.w(112, 108, 117, 103, 105, 110, 115, 47, 68, 72, 65, 110, 116, 105, 66, 111, 116, 47) + path);
        return read(f);
    }
}