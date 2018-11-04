/**
 * 2018. 10. 23
 * ���丮 ���� �� ���� ��� �ϱ� ���� Class
 * doc : ���丮 ������ ���� �����Ѵ�.
 * CD-01.[����]���丮�� �������� / ���丮 �Ǵ� ���ϸ��� �ִ��� üũ�ϴ� Method
 * CD-02.�ֻ��� ���丮 ���� (��¥�� ����)
 * CD-03.���丮 ���� �� ���� ����
 * URL :
 */
import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FilenameFilter;

//import sublime.ypPro.scr.main.java.temp.FileDirectoryDelete;

public class FileBackUp {

    //��� ��ġ ��� ���� [���� ��ġ]
    private static final String strBackUpFileDirUrl = "F:\\���̹���\\�ҽ����";
    //���� ���� ��ġ ��� ����
    private static final String strOrgFileDirUrl = "C:\\yongpal\\yps\\yps";

    //���� ����
   // private FileDirectoryDelete fileDirectoryDelete;

    // ����
    public static void main(String[] arg) {
        //���� ������ ���� �� ���� ���丮 ����
        String strChkResult = getDirNameChk();

       // if(strChkResult.equals("Yes")) {
            //System.out.println("directory Name Chack >>> ["+strChkResult+"]");
            //������ ���丮[���� ����]
            File fOldDir = new File(strOrgFileDirUrl);

            //����� ���丮
            String dChk = getDate("YYYYMM");
            String dDayChk = getDate("dd");
            File fNewDir = new File(strBackUpFileDirUrl+"\\"+dChk+"\\"+dDayChk);

            //�������� ����
            copyDirectory(fOldDir,fNewDir);
        //}
    }

    /* CD-03 2�ܰ� ���丮 / ����  ����
        ���� ���� ����
     */
    public static void copyDirectory(File fOldDir, File fNewDir){

            String[] children = fOldDir.list();

            int count = 0;

            for(int i=0; i < children.length ;  i++) {
                File fDir = new File(fOldDir+"\\"+children[i]);
                //File fCopyDir = new File(fNewDir+"\\"+dChk+"\\"+dDayChk+"\\"+children[i]);
                File fCopyDir = new File(fNewDir+"\\"+children[i]);
               // if(!children[i].equals(".settings") && !children[i].equals(".svn") && !children[i].equals("META-INF")  && !children[i].equals("source")){
                    //���丮 ó�� �κ�
                    if(fDir.isDirectory()) {
                        //���丮 Ȯ�� �� ����
                        if(!fCopyDir.exists()) {
                            fCopyDir.mkdir();
                        }

                        copyDirectory(new File(fOldDir,children[i]),new File(fNewDir,children[i]));
                    } else {
                        //���� ó�� �κ�
                        try {
                            InputStream in = new FileInputStream(fDir);
                            OutputStream out = new FileOutputStream(fCopyDir);
                            byte[] but = new byte[1024];
                            int len;

                            while((len = in.read(but)) > 0) {
                                out.write(but,0,len);
                            }
                            in.close();
                            out.close();
                        } catch(IOException e) {
                            e.printStackTrace();
                            System.out.println("IOException Error");
                        }  catch(Exception e) {
                            e.printStackTrace();
                            System.out.println("Exception Error");
                        }
                    }
                    System.out.println("File Back Up Load Status [Copy] -------");
                    count++;
            }
            //System.out.println("File Back Up Load Status [Copy Success] -------"+count);
    }

    //��¥�� �������� Method
    //���� ���
    //param  String : ��¥ ���� ������ �޴´�.
    //return Date
    public static String getDate(String strPattern) {
        //���� ������ ��¥�� �����´�.
        Date date = new Date();
        //Date Ŭ���� �����ϱ� (���� ����)
        SimpleDateFormat format = new SimpleDateFormat(strPattern);
        return format.format(date).toString();
    }

    /* CD-02 1�ܰ� ���丮 ������ ���� üũ
    ���丮���� �����ϴ��� üũ�ϰ� ���丮�� ���� �ϴ� Method
     */
    public static String getDirNameChk() {

        //���丮���� �����ϴ��� üũ �Ѵ�.
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir,String name) {
            File fTmp = new File(dir+"\\"+name);
            return fTmp.isDirectory();
        }
       };

        //���� URL �����Ѵ�.
        File fDir = new File(strBackUpFileDirUrl);

        //1. ���� ����� ���� �ֻ��� ���丮�� ���� ���� üũ
        String dChk = getDate("YYYYMM");
        String dDayChk = getDate("dd");
        boolean trueFalse = filter.accept(fDir,dChk);

        //���丮�� ���� �ϸ� ����� �ٽ� ����.
 /*       if(trueFalse) {
            File delDir = new File(strBackUpFileDirUrl+"\\"+dChk+"\\"+dDayChk);
            try {
                FileDirectoryDelete fileDirectoryDelete =new FileDirectoryDelete();

                fileDirectoryDelete.cleanDirectory(delDir);
                //fNewDir.delete();
                System.out.println("Directory has been deleted."+delDir);
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("IOException Error");
            }

/            if(fNewDir.deleteDirectory()) {
                System.out.println("Directory has been deleted.");
            }
            trueFalse = false;
        }*/
        //1-1 ���丮 ���� [�ְ� ���� ���丮 ����]
        if(!trueFalse) {
            File fNewDir = new File(strBackUpFileDirUrl+"\\"+dChk);
            fNewDir.mkdir();
        }

        //2. ���� ����� �� ���ں� ���丮 ���� ���� üũ
        File fNewDir = new File(strBackUpFileDirUrl+"\\"+dChk);
        //String dDayChk = getDate("dd");
        boolean dayYn = filter.accept(fNewDir,dDayChk);

        //2-1 ���丮 ���� [�ش� ���丮�� ���� ���丮 ����]
        if(!dayYn) {
            File fNewDayDir = new File(strBackUpFileDirUrl+"\\"+dChk+"\\"+dDayChk);
            fNewDayDir.mkdir();
        }
        System.out.println(dDayChk + "<>" +dayYn);

        String strResult = "No"; //��� ���� ��´�.
        if(dayYn && trueFalse) {
            strResult = "Yes";
        }

        return strResult;
    }

    /* CD-01 ����
        1. �ش� ���丮�� ���� ���丮���� �ҷ� �´�.
        2. ���丮���� �ִ��� üũ�Ѵ�.
     */
    public static void getDirectoryNameSearch(){

        //���丮 ���� �ϴ��� ã�´�.
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir,String name) {
            File fTmp = new File(dir+"\\"+name);
            System.out.println("File true / false Result >>> "+fTmp.isDirectory());
            return fTmp.isDirectory();
        }
       };

        // ���丮 ��� �����Ѵ�.
        //File fDir = new File("D:\\SDP-4.5.0\\workspace\\sdp-base");
        File fDir = new File("D:\\���\\source file");
        String[] dirList = fDir.list(filter);
        //���丮 ���� üũ
        boolean trueFalse = filter.accept(fDir,"src");
        System.out.println("<>"+trueFalse);
        if(dirList != null) {
            for(int i=0; i < dirList.length;i++) {
                System.out.println(dirList[i]);

            }
        }
    }
}
