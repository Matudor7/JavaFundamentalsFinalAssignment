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

        deserializeItems();
        deserializeMembers();
    }


    //As the assignment does not require serialization/storing of users, the users are hardcoded.
    //User information can also be found in README.md
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        users.add(new User("tudor", "tdrnsk", "Tudor"));
        users.add(new User("jake", "pass", "Jake"));

        return users;
    }

    public List<Item> getAllItems(){
        return items;
    }

    public Item getItemByCode(int code){
        for(Item item:items){
            if(item.getItemCode() == code){
                return item;
            }
        }
        return null;
    }

    public void lendItem(Item item){
        for(Item itemObject:items){
            if(itemObject.equals(item)){
                itemObject.setIsAvailable(false);
                itemObject.setTimeLent(LocalDate.now());
                break;
            }
        }
    }

    public void receiveItem(Item item){
        for(Item itemObject:items){
            if(itemObject.equals(item)){
                itemObject.setIsAvailable(true);
                itemObject.setTimeLent(null);
                break;
            }
        }
    }

    public Member getMemberByCode(int code){
        for(Member member:members){
            if(member.getMemberId() == code){
                return member;
            }
        }
        return null;
    }

    public void writeItemToDatabase(Item item){

        if(!items.isEmpty()){
            item.setItemCode(items.get(items.size() - 1).getItemCode() + 1);
        } else{
            item.setItemCode(1);
        }

        items.add(item);

    }

    public List<Member> getAllMembers(){
        return members;
    }

    public void writeMemberToDatabase(Member member){

        if (!members.isEmpty()) {
            member.setMemberId(members.get(members.size() - 1).getMemberId() + 1);
        } else {
            member.setMemberId(1);
        }
        members.add(member);
    }

    public void editItem(Item oldItem, Item newItem){

        for (Item item: items) {
            if(item.equals(oldItem)){
                item.setTitle(newItem.getTitle());
                item.setAuthor(newItem.getAuthor());
                break;
            }
        }
    }

    public void deleteItem(Item item){
        items.remove(item);
    }

    public void editMember(Member oldMember, Member newMember){

        for(Member member : members){
            if(member.equals(oldMember)){
                member.setFirstName(newMember.getFirstName());
                member.setLastName(newMember.getLastName());
                member.setBirthdate(newMember.getBirthdate());
                break;
            }
        }
    }

    public void deleteMember(Member member){
        members.remove(member);
    }

    public void serializeObjects() throws IOException{

        try(FileOutputStream fos = new FileOutputStream(new File("collection.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            for(Item item : items){
                oos.writeObject(item);
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        try(FileOutputStream fos = new FileOutputStream(new File("members.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            for(Member member : members){
                oos.writeObject(member);
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void deserializeMembers() throws IOException, ClassNotFoundException {

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("members.txt"))){
                while(true) {
                    try {
                        members.add((Member) ois.readObject());
                    }catch(EOFException e) {
                        break;
                    }
                }
        }catch(IOException e){
            writeMemberToDatabase(new Member("John", "Smith", LocalDate.of(1998, 8,12)));
            writeMemberToDatabase(new Member("Sarah", "Davidson", LocalDate.of(1976, 11, 6)));
        }

    }

    public void deserializeItems() throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("collection.txt"))){
            while(true){
                try{
                    items.add((Item) ois.readObject());
                }catch(EOFException e){
                    break;
                }
            }
        }catch(IOException e){
            writeItemToDatabase(new Item(true, "The Long Walk", "King, Stephen"));
            writeItemToDatabase(new Item(false, "The Bell Jar", "Plath, Sylvia", LocalDate.of(2022, 3, 13)));
        }
    }
}
