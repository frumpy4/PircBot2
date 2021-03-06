package PircBot;

import java.util.concurrent.*;
import java.util.*;

/**
 * Defines a channel object.
 *
 * @author Michael
 * @created 1/15/2016 @ 7:42am
 */
//broadcaster-lang=;r9k=0;slow=0;subs-only=0
public class Channel implements Iterable<User> {

    private ConcurrentHashMap<String, User> users;
    private String name;
    private String server;
    private String broadcasterLanguage = "";
    private boolean r9k = false;
    private int slow = 0;
    private boolean subsOnly = false;

    /**
     * Constructs a new Channel Object with only the name and server. This
     * constructor will make a blank Userlist.
     *
     * @param name Name of the channel, prefixed with #
     * @param server Server IP
     */
    public Channel(String name, String server) {
        this.name = name;
        this.server = server;
        users = new ConcurrentHashMap<>();
    }

    /**
     * Constructs a new Channel Object with a list of Users, the Channel Name,
     * and the Server.
     *
     * @param users ConcurrentHashMap of the userlist
     * @param name Channel Name, prefixed with #
     * @param server Server IP
     */
    public Channel(ConcurrentHashMap<String, User> users, String name, String server) {
        this.users = users;
        this.name = name;
        this.server = server;
    }

    /**
     * Constructs a new Channel Object with a list of Users, the Channel Name,
     * and the Server.
     *
     * @param users User array for the Userlist
     * @param name Channel Name, prefixed with #
     * @param server Server IP
     */
    public Channel(User[] users, String name, String server) {
        this.name = name;
        this.server = server;
        this.users = new ConcurrentHashMap<>();
        for (User el : users) {
            this.users.put(el.getNick(), el);
        }
    }

    /**
     * Constructs a new Channel Object with a list of Users, the Channel Name,
     * and the Server.
     *
     * @param users Userlist
     * @param name Channel Name, prefixed with #
     * @param server Server IP
     */
    public Channel(List<User> users, String name, String server) {
        this(users.toArray(new User[users.size()]), name, server);
    }

    /**
     * Returns a single user object.
     *
     * @param user Name of the user to look for
     * @return User requested, null if not found.
     */
    public User getUser(String user) {
        user = user.toLowerCase();
        return users.get(user);
    }

    /**
     * Adds a user to the userlist.
     *
     * @param user User object to add
     */
    public void addUser(User user) {
        users.put(user.getNick().toLowerCase(), user);
    }

    /**
     * Updates a user object. It's recommended that you call the getUser(user)
     * method before calling this method, so that you can update the user
     * correctly.
     *
     * @param user User Object to update
     */
    public void updateUser(User user) {
        this.addUser(user);
    }

    /**
     * Removes a user from the userlist.
     *
     * @param user User object to remove
     */
    public void removeUser(User user) {
        users.remove(user.getNick().toLowerCase());
    }

    /**
     * Removes a user from the userlist.
     *
     * @param nick Username to remove
     */
    public void removeUser(String nick) {
        nick = nick.toLowerCase();
        users.remove(nick);
    }

    public boolean containsUser(String nick) {
        return users.containsKey(nick);
    }

    public boolean containsUser(User user) {
        return users.containsValue(user);
    }

    /**
     * Gets the current userlist.
     *
     * @return ArrayList containing user objects representing the userlist
     */
    public ArrayList<User> getUserlist() {
        ArrayList<User> toReturn = new ArrayList<>();
        users.values().stream().forEach((el) -> {
            toReturn.add(el);
        });
        Collections.sort(toReturn);
        return toReturn;
    }

    /**
     * Returns the size of the Userlist.
     *
     * @return Count of the users in the channel
     */
    public int getUserCount() {
        return users.size();
    }

    /**
     * Returns the channel name.
     *
     * @return Current Channel Name
     */
    public String getChannelName() {
        return name;
    }

    /**
     * Returns the current server IP
     *
     * @return Server IP
     */
    public String getServer() {
        return server;
    }

    /**
     * Returns the broadcasting language of the channel. This will only return a
     * value if the tags are enabled and a language is set.
     *
     * @return Broadcasting Language of the channel
     */
    public String getBroadcasterLanguage() {
        return broadcasterLanguage;
    }

    /**
     * Sets the broadcast language of the channel.
     *
     * @param broadcasterLanguage Language
     */
    public void setBroadcasterLanguage(String broadcasterLanguage) {
        this.broadcasterLanguage = broadcasterLanguage;
    }

    /**
     * Checks if the Channel is in R9K mode.
     *
     * @return True if in R9K, false if not
     */
    public boolean isR9k() {
        return r9k;
    }
    /**
     * Sets the R9K Status of the channel.
     * @param r9k R9K Status
     */
    public void setR9k(boolean r9k) {
        this.r9k = r9k;
    }
    /**
     * Returns the status of Slow Mode on the channel.
     * @return True if Slow Mode > 0, false if Slow Mode = 0.
     */
    public boolean isSlow() {
        return slow == 0;
    }
    /**
     * Sets the slow mode for the channel.
     * @param slow Integer for number of seconds between messages.
     */
    public void setSlow(int slow) {
        this.slow = slow;
    }
    /**
     * Checks the Sub-Only chat status.
     * @return True if the channel is in Sub-Only mode, false otherwise
     */
    public boolean isSubsOnly() {
        return subsOnly;
    }
    /**
     * Sets the Sub-Only status in the channel.
     * @param subsOnly Sub-Only status.
     */
    public void setSubsOnly(boolean subsOnly) {
        this.subsOnly = subsOnly;
    }

    @Override
    public Iterator<User> iterator() {
        return getUserlist().iterator();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.users);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.server);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Channel other = (Channel) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.server, other.server);
    }

    @Override
    public String toString() {
        return name;
    }

}
