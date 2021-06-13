package com;

import com.utils.DataUtil;
import com.vo.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefectsDB {
    public static List<SIR> sirs;
    private static Map<String, String> sirBugMap;


    public static List<SIR> initDB() {
        Document document = DataUtil.getDocument();
        NodeList sirList = document.getElementsByTagName(Constant.XML_SIR_LABEL);
        int sirLength = sirList.getLength();
        sirs = new ArrayList<>(sirLength);
        for (int i = 0; i < sirLength; i++) {
            Element sirItem = (Element) sirList.item(i); //一个sir
            String sirName = sirItem.getElementsByTagName(Constant.XML_SIR_NAME_LABEL).item(0).getTextContent();
            String source = sirItem.getElementsByTagName(Constant.XML_SIR_SOURCE_LABEL).item(0).getTextContent();
            SIR sir = new SIR(sirName,source);
            //一个sir对应多个bug
            NodeList bugList = sirItem.getElementsByTagName(Constant.XML_BUG_LABEL);
            for (int j = 0; j < bugList.getLength(); j++) {
                Element bugItem = (Element) bugList.item(j);
                String bugID = bugItem.getElementsByTagName(Constant.XML_BUG_ID_LABEL).item(0).getTextContent();
                String errorMessage = bugItem.getElementsByTagName(Constant.XML_BUG_ERRORMESSAGE).item(0).getTextContent();
                String describe =bugItem.getElementsByTagName(Constant.XML_BUG_DESCRIBE).item(0).getTextContent();
                String type = bugItem.getElementsByTagName(Constant.XML_BUG_TYPE).item(0).getTextContent();

                String operateScore = bugItem.getElementsByTagName(Constant.XML_OPERATE_SCORE).item(0).getTextContent();
                String localScore = bugItem.getElementsByTagName(Constant.XML_BUG_LOCAL_SCORE).item(0).getTextContent();
                String fixLength = bugItem.getElementsByTagName(Constant.XML_BUG_FIX_LENGTH).item(0).getTextContent();
                String character = bugItem.getElementsByTagName(Constant.XML_BUG_CHARACTER).item(0).getTextContent();
                String issue_url = bugItem.getElementsByTagName(Constant.XML_ISSUE_URL).item(0).getTextContent();
                String commit_url = bugItem.getElementsByTagName(Constant.XML_COMMIT_URL).item(0).getTextContent();
                //buggyVersion和fixVersion为对象类型
                NodeList buggyVersion = bugItem.getElementsByTagName(Constant.XML_BUGGY_VERSION);
                //List<BuggyVersion> buggyVersions = new ArrayList<BuggyVersion>();
                Element bVersion = (Element) buggyVersion.item(0);
                String buggytestCmd = bVersion.getElementsByTagName(Constant.XML_BUGGY_TEST_CMD_LABEL).item(0).getTextContent();
                String buggycommit = bVersion.getElementsByTagName(Constant.XML_BUGGY_COMMIT_LABEL).item(0).getTextContent();
                BuggyVersion BuggyVersion = new BuggyVersion(buggytestCmd,buggycommit);
                //buggyVersions.add(new BuggyVersion(buggytestCmd, buggycommit));

                NodeList fixVersion = bugItem.getElementsByTagName(Constant.XML_FIX_VERSION);
                Element fVersion = (Element) fixVersion.item(0);
                String fixtestCmd = fVersion.getElementsByTagName(Constant.XML_FIX_TEST_CMD_LABEL).item(0).getTextContent();
                String fixcommit = fVersion.getElementsByTagName(Constant.XML_FIX_COMMIT_LABEL).item(0).getTextContent();
                FixVersion FixVersion = new FixVersion(fixtestCmd,fixcommit);
                sir.addBug(new Bug(bugID, errorMessage,describe,operateScore,type,BuggyVersion,FixVersion,localScore,fixLength,character,issue_url,commit_url));

            }
            sirs.add(sir);
        }
        //return initSirBugMap();
        initSirBugMap();
        return sirs;

    }

    private static String getContentText(Element parent,String label) {
        Node target =parent.getElementsByTagName(label).item(0);
        String result="";
        if (target!=null) {
            result=target.getTextContent();
        }
        return result;
    }
    private static Map<String, String> initSirBugMap() {
        sirBugMap = new HashMap<>();
        Map<String, String> resMap = new HashMap<>();
        sirs.forEach((sir) -> {
            sir.getBugs().forEach((bug) -> {
                sirBugMap.put(bug.getBugID(), sir.getSIRName());
                resMap.put(bug.getBugID(), sir.getSIRName());
            });
        });
        return resMap;
    }

    public static String getSirName(String bugID) {
        return sirBugMap.get(bugID);
    }

    public static String[] bugIDsToArray() {
        List<Bug> bugs = new ArrayList<>();
        sirs.forEach((sir) -> {
            bugs.addAll(sir.getBugs());
        });
        String[] bugIDs = new String[bugs.size()];
        int i = 0;
        for (Bug bug : bugs) {
            bugIDs[i] = bug.getBugID();
            ++i;
        }
        return bugIDs;
    }

    public static boolean contains(String sirName) {
        sirName = sirName.toLowerCase().trim();
        for (SIR sir : sirs) {
            if (sir.getSIRName().equals(sirName)) {
                return true;
            }
        }
        return false;
    }

    public static SIR getSIR(String sirName) {

        for (SIR sir : sirs) {
            if (sir.getSIRName().equals(sirName)) {
                return sir;
            }
        }
        return null;
    }

    public static Bug getBug(String bugID) throws Exception {

        for (SIR sir : sirs) {
            for (Bug bug : sir.getBugs()) {
                if (bug.getBugID().equals(bugID)) {
                    return bug;
                }
            }
        }
        throw new Exception(Constant.BUG_NOTHAVEN_INFO_EXCEPTION);
    }
}
