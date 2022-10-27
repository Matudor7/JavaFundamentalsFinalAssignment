package librarymanager.data;

import librarymanager.model.Item;
import librarymanager.model.Member;
import librarymanager.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database{
    FileOutputStream itemFos;
    ObjectOutputStream itemOos;

    FileInputStream itemFis;
    ObjectInputStream itemOis;

    FileOutputStream memberFos;
    ObjectOutputStream memberOos;

    FileInputStream memberFis;
    ObjectInputStream memberOis;

    public Database() throws IOException, ClassNotFoundException{
        itemFos = new FileOutputStream("collection.txt", true);
        itemOos = new ObjectOutputStream(itemFos);
        itemFis = new FileInputStream("collection.txt");
        itemOis = new ObjectInputStream(itemFis);

        memberFos = new FileOutputStream("members.txt", true);
        memberOos = new ObjectOutputStream(memberFos);
        memberFis = new FileInputStream("members.txt");
        memberOis = new ObjectInputStream(memberFis);

        //Method below is used to write some sample items and members. Keeping the method in the constructor will keep
        //appending the same objects to the files.
        //WriteSampleItemsAndMembers();
    }

    public void WriteSampleItemsAndMembers() throws IOException, ClassNotFoundException {
        WriteMemberToDatabase(new Member("John", "Smith", LocalDate.of(1998, 8,12)));
        WriteMemberToDatabase(new Member("Sarah", "Davidson", LocalDate.of(1976, 11, 6)));

        WriteItemToDatabase(new Item(true, "Title of the Book 2", "de Vries, Peter"));
        WriteItemToDatabase(new Item(false, "Title of the Book 1", "Nosca, Tudor Matei"));
    }


    //As the assignment does not require serialization/storing of users, the users are hardcoded.
    //User information can also be found in README.md
    public List<User> GetAllUsers(){
        List<User> users = new ArrayList<User>();

        users.add(new User("tudornosca", "tdrnsk678549", "Tudor"));
        users.add(new User("jakesmith", "otto65487", "Jake"));

        return users;
    }

    public List<Item> GetAllItems() throws IOException, ClassNotFoundException{
        List<Item> items = new ArrayList<Item>();

        try{
            while(true){
                items.add((Item) itemOis.readObject());
            }
        }catch(Exception e){
            //Do nothing, this acts like a break statement. Not sure if there is a nicer way of iterating through a serialized file
        }
        return items;
    }

    public void WriteItemToDatabase(Item item) throws IOException, ClassNotFoundException{
        List<Item> items = GetAllItems();

        if(!items.isEmpty()){
            item.setItemCode(items.get(items.size() - 1).getItemCode() + 1);
        } else{
            item.setItemCode(1);
        }

        items.add(item);

        for(Item itemObject : items){
            itemOos.writeObject(itemObject);
        }
    }

    public List<Member> GetAllMembers() throws IOException, ClassNotFoundException{
        List<Member> members = new ArrayList<Member>();
        try{
            while(true){
                members.add((Member) memberOis.readObject());
            }
        }catch(Exception e){
            //Do nothing, this acts like a break statement.
        }

        return members;
    }

    public void WriteMemberToDatabase(Member member) throws IOException, ClassNotFoundException {
        List<Member> members = GetAllMembers();

        if (!members.isEmpty()) {
            member.setMemberId(members.get(members.size() - 1).getMemberId() + 1);
        } else {
            member.setMemberId(1);
        }

        members.add(member);

        for (Member memberObject : members) {
            memberOos.writeObject(memberObject);
        }

    }

    public void CloseStreams() throws IOException {
        memberOos.flush();
        memberOos.close();
        itemOos.flush();
        itemOos.close();
        memberOis.close();
        itemOis.close();
    }
}
