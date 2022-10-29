package nl.inholland.nl.tudornosca678549libraryassignment.data;

import nl.inholland.nl.tudornosca678549libraryassignment.model.Item;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Member;
import nl.inholland.nl.tudornosca678549libraryassignment.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    FileOutputStream itemFos;
    ObjectOutputStream itemOos;

    FileInputStream itemFis;
    ObjectInputStream itemOis;

    FileOutputStream memberFos;
    ObjectOutputStream memberOos;

    FileInputStream memberFis;
    ObjectInputStream memberOis;

    List<Item> items;

    List<Member> members;

    public Database() throws IOException, ClassNotFoundException{
        items = new ArrayList<>();
        members = new ArrayList<>();

        itemFos = new FileOutputStream("collection.txt");
        itemOos = new ObjectOutputStream(itemFos);

        memberFos = new FileOutputStream("members.txt");
        memberOos = new ObjectOutputStream(memberFos);
        //Method below is used to write some sample items and members. Keeping the method in the constructor will keep
        //appending the same objects to the files.
        WriteSampleItemsAndMembers();
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
        List<User> users = new ArrayList<>();

        users.add(new User("tudor", "tdrnsk", "Tudor"));
        users.add(new User("jake", "pass", "Jake"));

        return users;
    }

    public List<Item> GetAllItems() throws IOException, ClassNotFoundException{
        return items;
    }

    public void WriteItemToDatabase(Item item) throws IOException, ClassNotFoundException{

        if(!items.isEmpty()){
            item.setItemCode(items.get(items.size() - 1).getItemCode() + 1);
        } else{
            item.setItemCode(1);
        }

        items.add(item);

        itemOos.writeObject(item);

    }

    public List<Member> GetAllMembers(){
        return members;
    }

    public void WriteMemberToDatabase(Member member){

        if (!members.isEmpty()) {
            member.setMemberId(members.get(members.size() - 1).getMemberId() + 1);
        } else {
            member.setMemberId(1);
        }
        members.add(member);
    }

    public void editItem(Item oldItem, Item newItem){

        for (Item item: items) {
            if(item.getTitle().equals(oldItem.getTitle()) && item.getAuthor().equals(oldItem.getAuthor())){
                item.setTitle(newItem.getTitle());
                item.setAuthor(newItem.getAuthor());
                break;
            }
        }
    }

    public void deleteItem(Item item){
        items.remove(item);
    }

    public void deleteMember(Member member){

    }

    public void serializeObjects() throws IOException{
        for(Member member : members){
            memberOos.writeObject(member);
        }
    }

    public void deserializeObjects() throws IOException {
        memberFis = new FileInputStream("members.txt");
        memberOis = new ObjectInputStream(memberFis);

        try{
            while(true){
                memberOis.readObject();
            }
        }catch(Exception e){
            //Do nothing, this acts like a break statement. Not sure if there is a nicer way of iterating through a serialized file
        }

        itemFis = new FileInputStream("collection.txt");
        itemOis = new ObjectInputStream(itemFis);

        try{
            while(true){
                items.add((Item) itemOis.readObject());
            }
        }catch(Exception e){
            //Do nothing, this acts like a break statement.
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
