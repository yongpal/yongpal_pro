/**
 * 2018. 10. 23
 * 디렉토리 생성 및 파일 백업 하기 위한 Class
 * doc : 디렉토리 단위로 파일 복사한다.
 * CD-01.[샘플]디렉토리명 가져오기 / 디랙토리 또는 파일명이 있는지 체크하는 Method
 * CD-02.최상위 디렉토리 생성 (날짜별 생성)
 * CD-03.디렉토리 생성 및 파일 복사
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

    //백업 위치 경로 설정 [최초 위치]
    private static final String strBackUpFileDirUrl = "F:\\마이문서\\소스백업";
    //원본 파일 위치 경로 설정
    private static final String strOrgFileDirUrl = "C:\\yongpal\\yps\\yps";

    //파일 삭제
   // private FileDirectoryDelete fileDirectoryDelete;

    // 실행
    public static void main(String[] arg) {
        //기존 데이터 삭제 및 상위 디렉토리 생성
        String strChkResult = getDirNameChk();

       // if(strChkResult.equals("Yes")) {
            //System.out.println("directory Name Chack >>> ["+strChkResult+"]");
            //복사할 디렉토리[원본 파일]
            File fOldDir = new File(strOrgFileDirUrl);

            //복사될 디렉토리
            String dChk = getDate("YYYYMM");
            String dDayChk = getDate("dd");
            File fNewDir = new File(strBackUpFileDirUrl+"\\"+dChk+"\\"+dDayChk);

            //최종으로 저저
            copyDirectory(fOldDir,fNewDir);
        //}
    }

    /* CD-03 2단계 디렉토리 / 파일  복사
        최종 본문 내용
     */
    public static void copyDirectory(File fOldDir, File fNewDir){

            String[] children = fOldDir.list();

            int count = 0;

            for(int i=0; i < children.length ;  i++) {
                File fDir = new File(fOldDir+"\\"+children[i]);
                //File fCopyDir = new File(fNewDir+"\\"+dChk+"\\"+dDayChk+"\\"+children[i]);
                File fCopyDir = new File(fNewDir+"\\"+children[i]);
               // if(!children[i].equals(".settings") && !children[i].equals(".svn") && !children[i].equals("META-INF")  && !children[i].equals("source")){
                    //디렉토리 처리 부분
                    if(fDir.isDirectory()) {
                        //디렉토리 확인 후 생성
                        if(!fCopyDir.exists()) {
                            fCopyDir.mkdir();
                        }

                        copyDirectory(new File(fOldDir,children[i]),new File(fNewDir,children[i]));
                    } else {
                        //파일 처리 부분
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

    //날짜를 가져오는 Method
    //공통 요소
    //param  String : 날짜 포맷 형식을 받는다.
    //return Date
    public static String getDate(String strPattern) {
        //현재 기준의 날짜를 가져온다.
        Date date = new Date();
        //Date 클래스 포장하기 (포맷 설정)
        SimpleDateFormat format = new SimpleDateFormat(strPattern);
        return format.format(date).toString();
    }

    /* CD-02 1단계 디렉토리 생성을 위한 체크
    디렉토리명이 존재하는지 체크하고 디렉토리를 생성 하는 Method
     */
    public static String getDirNameChk() {

        //디렉토리명이 존재하는지 체크 한다.
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir,String name) {
            File fTmp = new File(dir+"\\"+name);
            return fTmp.isDirectory();
        }
       };

        //파일 URL 지정한다.
        File fDir = new File(strBackUpFileDirUrl);

        //1. 현재 년월에 대한 최상위 디렉토리에 대한 유무 체크
        String dChk = getDate("YYYYMM");
        String dDayChk = getDate("dd");
        boolean trueFalse = filter.accept(fDir,dChk);

        //디렉토리가 존재 하면 지우고 다시 쓴다.
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
        //1-1 디렉토리 생성 [최고 상위 디렉토리 생성]
        if(!trueFalse) {
            File fNewDir = new File(strBackUpFileDirUrl+"\\"+dChk);
            fNewDir.mkdir();
        }

        //2. 현재 년월에 들어갈 일자별 디렉토리 대한 유무 체크
        File fNewDir = new File(strBackUpFileDirUrl+"\\"+dChk);
        //String dDayChk = getDate("dd");
        boolean dayYn = filter.accept(fNewDir,dDayChk);

        //2-1 디렉토리 생성 [해당 디렉토리의 하위 디렉토리 생성]
        if(!dayYn) {
            File fNewDayDir = new File(strBackUpFileDirUrl+"\\"+dChk+"\\"+dDayChk);
            fNewDayDir.mkdir();
        }
        System.out.println(dDayChk + "<>" +dayYn);

        String strResult = "No"; //결과 값을 담는다.
        if(dayYn && trueFalse) {
            strResult = "Yes";
        }

        return strResult;
    }

    /* CD-01 샘플
        1. 해당 디렉토리의 하위 디렉토리명을 불러 온다.
        2. 디렉토리명이 있는지 체크한다.
     */
    public static void getDirectoryNameSearch(){

        //디렉토리 존재 하는지 찾는다.
        FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir,String name) {
            File fTmp = new File(dir+"\\"+name);
            System.out.println("File true / false Result >>> "+fTmp.isDirectory());
            return fTmp.isDirectory();
        }
       };

        // 디렉토리 경로 지정한다.
        //File fDir = new File("D:\\SDP-4.5.0\\workspace\\sdp-base");
        File fDir = new File("D:\\백업\\source file");
        String[] dirList = fDir.list(filter);
        //디렉토리 유무 체크
        boolean trueFalse = filter.accept(fDir,"src");
        System.out.println("<>"+trueFalse);
        if(dirList != null) {
            for(int i=0; i < dirList.length;i++) {
                System.out.println(dirList[i]);

            }
        }
    }
}
