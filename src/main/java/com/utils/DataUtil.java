package com.utils;

import com.vo.Bug;
import com.vo.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DataUtil {

    //public final static String DBFILEPATH = "Metadata" + File.separator + "Database.xml";
    public final static String DBFILEPATH = "resources/Database.xml";
    static Document doc = null;
    public static Document getDocument() {

        //File bug_report = new File(DBFILEPATH);
        InputStream bug_report = null;
        ClassPathResource resource = new ClassPathResource(DBFILEPATH);
        try {
            bug_report = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(bug_report);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    //add bug
    public static void addBug(String sirName, Bug bug) {
        getDocument();
        //<sir>
        Element sirEle = getSIR(sirName);
        // if bug exist,need not add
        NodeList bugNodeList = sirEle.getElementsByTagName(Constant.XML_BUG_LABEL);
        if (containsBug(bugNodeList, bug.getBugID())) {
            return;
        }
        //<bug> add <bug> to <sir>
        Element bugEle = doc.createElement(Constant.XML_BUG_LABEL);
        sirEle.appendChild(bugEle);
        //<bug> child
        Element bugIDEle = doc.createElement(Constant.XML_BUG_ID_LABEL);
        bugIDEle.appendChild(doc.createTextNode(bug.getBugID()));
        bugEle.appendChild(bugIDEle);

        Element buggyVersionEle = doc.createElement(Constant.XML_BUGGY_VERSION);
        Element buggytestCmdEle = doc.createElement(Constant.XML_BUGGY_TEST_CMD_LABEL);
        buggytestCmdEle.appendChild(doc.createTextNode(bug.getBuggyVersion().getBuggytestCmd()));
        Element buggycommitEle = doc.createElement(Constant.XML_BUGGY_COMMIT_LABEL);
        buggycommitEle.appendChild(doc.createTextNode(bug.getBuggyVersion().getBuggycommit()));

        Element fixVersionEle = doc.createElement(Constant.XML_FIX_VERSION);
        Element fixtestCmdEle = doc.createElement(Constant.XML_FIX_TEST_CMD_LABEL);
        fixtestCmdEle.appendChild(doc.createTextNode(bug.getFixVersion().getFixtestCmd()));
        Element fixcommitEle = doc.createElement(Constant.XML_FIX_COMMIT_LABEL);
        fixcommitEle.appendChild(doc.createTextNode(bug.getFixVersion().getFixcommit()));

        buggyVersionEle.appendChild(buggytestCmdEle);
        buggyVersionEle.appendChild(buggycommitEle);
        fixVersionEle.appendChild(fixtestCmdEle);
        fixVersionEle.appendChild(fixcommitEle);
        bugEle.appendChild(buggyVersionEle);
        bugEle.appendChild(fixVersionEle);

        write();
    }

    public static void write() {
        try {
            DOMSource domSource = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // File file = new File(DBFILEPATH);
            File file = ResourceUtils.getFile(DBFILEPATH);

            if (!file.exists()) {
                file.createNewFile();
            }
            StreamResult sr = new StreamResult(file);
            transformer.transform(domSource, sr);

        } catch (Exception ex) {
            Logger.getLogger(DataUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Element getSIR(String sirName) {
        Element root = (Element) doc.getElementsByTagName(Constant.XML_ROOT_LABEL).item(0);
        NodeList sirNodeList = root.getElementsByTagName(Constant.XML_SIR_LABEL);
        for (int i = 0; i < sirNodeList.getLength(); i++) {
            Element sir = (Element) sirNodeList.item(i);
            String name = sir.getElementsByTagName(Constant.XML_SIR_NAME_LABEL).item(0).getTextContent();
            if (name.equals(sirName)) {
                return sir;
            }
        }
        Element eleName = doc.createElement(Constant.XML_SIR_NAME_LABEL);
        eleName.appendChild(doc.createTextNode(sirName));
        Element sirEle = doc.createElement(Constant.XML_SIR_LABEL);
        sirEle.appendChild(eleName);
        root.appendChild(sirEle);
        return sirEle;
    }

    public static boolean containsBug(NodeList nodeList, String bugID) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element bug = (Element) nodeList.item(i);
            String id = bug.getElementsByTagName(Constant.XML_BUG_ID_LABEL).item(0).getTextContent();
            if (id.equals(bugID)) {
                return true;
            }
        }
        return false;
    }

}
