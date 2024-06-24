package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schemawithplugin.ObjectFactory;
import ru.javaops.masterjava.xml.schemawithplugin.Payload;
import ru.javaops.masterjava.xml.schemawithplugin.User;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.stream.Collectors;

public class JaxbMainXml {

    private static JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    public void printParticipants(String projectName) throws JAXBException, IOException {
        ((Payload) JAXB_PARSER.unmarshal(Resources.getResource("payload.xml").openStream())).getProjects()
                .getProject().stream().filter(project -> projectName.equals(project.getName()))
                .flatMap(project -> project.getGroups().getGroup().stream())
                .flatMap(group -> group.getParticipants().getUser().stream()).map(User::getFullName).collect(Collectors.toSet())
                .stream().sorted()
                .forEach(System.out::println);
    }
}
