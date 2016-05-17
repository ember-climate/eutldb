package org.sandbag.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 10/05/16.
 */
public class ExecuteFile {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("This program expects the following parameters: " + "\n"
                    + "1. XML file including scheduled executions.");
        } else {


            try {

                File xmlFile = new File(args[0]);

                SAXBuilder builder = new SAXBuilder();

                Document document = (Document) builder.build(xmlFile);
                Element rootNode = document.getRootElement();

                List<Element> executions = rootNode.getChildren("execution");

                for (int i = 0; i < executions.size(); i++) {

                    String classFullName = "";
                    List<String> arguments = new LinkedList<>();

                    Element execution = executions.get(i);

                    classFullName = execution.getChildText("class_full_name");
                    List<Element> argumentElems = execution.getChild("arguments").getChildren("argument");

                    for (int j = 0; j < argumentElems.size(); j++) {

                        Element argumentElem = argumentElems.get(j);

                        arguments.add(argumentElem.getText());
                    }

                    Class classToExecute = Class.forName(classFullName);

                    Object object = classToExecute.newInstance();
                    Executable executable = (Executable) object;
                    executable.execute(arguments);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
