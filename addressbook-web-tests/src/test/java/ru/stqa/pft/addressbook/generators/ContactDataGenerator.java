package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contacts count")
  int count;
  @Parameter(names = "-f", description = "Target File")
  String file;
  @Parameter(names = "-d", description = "Format Data")
  String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    }catch (ParameterException e){
      jCommander.usage();
    }
    generator.run();

  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if(format.equals("csv")){
      saveAsCsv(contacts, new File(file));
    }else if(format.equals("xml")){
      saveAsXml(contacts, new File(file));
    }else if(format.equals("json")){
      saveAsJson(contacts, new File(file));
    }else {
      System.out.println("Unrecognizable format: " +format);
    }

  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try(Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xStream = new XStream();
    xStream.processAnnotations(ContactData.class);
    String xml = xStream.toXML(contacts);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();

  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData item : contacts){
      writer.write(String.format("%s;%s;%s;%s;%s\n", item.getFirstname(), item.getLastname(),
              item.getHomePhone(), item.getAddress(), item.getEmail()));
    }
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
      List<ContactData> contacts = new ArrayList<>();
      for (int i = 0; i < count; i++){
        contacts.add(new ContactData().withFirstname(String.format("FirstName-%s", i))
                .withMiddlename(String.format("MiddleName-%s", i)).withLastname(String.format("LastName-%s", i))
                .withNickname(String.format("NickName-%s", i)).withCompany(String.format("Company-%s", i))
                .withAddress(String.format("Address-%s", i)).withHomePhone(String.format("HomePhone-%s", i))
                .withMobilePhone(String.format("MobilePhone-%s", i)).withWorkPhone(String.format("WorkPhone-%s", i))
                .withDay("1").withMonth("January").withYear("1900").withNotes(String.format("HO-%s-Ho", i))
                .withEmail(String.format("email-%s", i)));
      }
      return contacts;
  }
}
