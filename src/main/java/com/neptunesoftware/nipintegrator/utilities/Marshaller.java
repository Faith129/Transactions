package com.neptunesoftware.nipintegrator.utilities;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Objects;

public interface Marshaller {
    static <T, U> U convertXmlStringToObject(final T string,  U object) {
        Objects.requireNonNull(string); Objects.requireNonNull(object);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = (U) unmarshaller.unmarshal(new StringReader(string.toString()));
            return object;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    static <T, U> U convertXmlStringToObject(final T t,  Class<U> u) {
        Objects.requireNonNull(t); Objects.requireNonNull(u);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(u);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            u = (Class<U>) unmarshaller.unmarshal(new StringReader(t.toString()));
            return (U) u;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getInfo(String name, Class<?> clazz) {
        try {
            InputStream is = clazz.getResourceAsStream(name);
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            String content = new String(data);
            return content;
        } catch (Exception ex) {
            System.out.println("Input File Not Found");
            //ex.printStackTrace();
            return null;
        }
    }


    static <U> String convertObjectToXmlString(final U object) {
        Objects.requireNonNull(object);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(object, sw);
            String xmlString = sw.toString();
            Document doc = convertStringToDocument(xmlString);
            assert doc != null;
            return doc.createCDATASection(xmlString).toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    static String convertDocumentToString(final Document doc) {
        Objects.requireNonNull(doc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }

    static Document convertStringToDocument(final String xmlStr) {
        Objects.requireNonNull(xmlStr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static <U> U convertXmlToObject(File f, U u) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(u.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            u = (U) jaxbUnmarshaller.unmarshal(f);
            return (U) u;
        } catch (JAXBException e) {
            throw new RuntimeException(e);

        }
    }
}
