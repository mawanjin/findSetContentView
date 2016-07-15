import java.io.*;

public class Main {
    static String smail;
    static String output;

    public static void main(String[] args) {

        if(args.length==0){
            System.out.println("Please input the path of directory 'smail' and output file path!");
            return;
        }

        if ((args.length==1)){
            System.out.println("Please input the path of output file !");
            return;
        }
        smail = args[0];
        output = args[1];

        if(!new File(output).exists() || new File(output).isDirectory()){
            System.out.println("output file is not exists or is not a file!");
            return;
        }

        new File(output).delete();
        try {
            new File(output).createNewFile();
        } catch (IOException e) {

        }

        File smailDir = new File(args[0]);
        if(!smailDir.exists()){
            System.out.println("The specified directory is not exists.["+smailDir+"]");
            return;
        }
        if(!smailDir.isDirectory()){
            System.out.println("The specified directory is not a directory.["+smailDir+"]");
            return;
        }
        process(smailDir);
    }

    private static void process(File file) {
        System.out.println("Start process file : "+file.getAbsolutePath());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files.length>0){
                for(File f: files){
                    if(f.isDirectory()){
                        process(f);
                    }else{
                        BufferedReader bufferedReader = null;
                        BufferedWriter writer = null;
                        //todo read the file to find the method 'setContentView'
                        try {
                            bufferedReader = new BufferedReader(new FileReader(f));

                            String str;
                            while ((str=bufferedReader.readLine())!=null){
                                if(str.contains("setContentView")){
                                    System.out.println("Got You!!! "+str);
                                    File outputFile = new File(output);
                                    writer  = new BufferedWriter(new FileWriter(outputFile,true));
                                    writer.write(f.getAbsolutePath().replace(smail,"")+"\t" + str);
                                    writer.write("\n");
                                    writer.flush();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                if(bufferedReader!=null)bufferedReader.close();
                                if(writer!=null)writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }

    }

}
