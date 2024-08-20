import java.util.*;

class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class Bid {
    private User bidder;
    private double amount;

    public Bid(User bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public User getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }
}

class Auction {
    private String item;
    private double startingPrice;
    private Date endTime;
    private List<Bid> bids;

    public Auction(String item, double startingPrice, Date endTime) {
        this.item = item;
        this.startingPrice = startingPrice;
        this.endTime = endTime;
        this.bids = new ArrayList<>();
    }

    public String getItem() {
        return item;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void addBid(Bid bid) {
        if (new Date().before(endTime)) {
            bids.add(bid);
            System.out.println("Bid of " + bid.getAmount() + " by " + bid.getBidder().getName() + " has been placed.");
        } else {
            System.out.println("Auction has ended. No more bids are accepted.");
        }
    }

    public Bid getHighestBid() {
        return bids.stream().max(Comparator.comparingDouble(Bid::getAmount)).orElse(null);
    }

    public void showBids() {
        for (Bid bid : bids) {
            System.out.println("Bidder: " + bid.getBidder().getName() + " | Amount: " + bid.getAmount());
        }
    }
}

class AuctionSystem {
    private List<User> users;
    private List<Auction> auctions;

    public AuctionSystem() {
        this.users = new ArrayList<>();
        this.auctions = new ArrayList<>();
    }

    public User registerUser(String name, String email) {
        User user = new User(name, email);
        users.add(user);
        return user;
    }

    public Auction createAuction(String item, double startingPrice, Date endTime) {
        Auction auction = new Auction(item, startingPrice, endTime);
        auctions.add(auction);
        return auction;
    }

    public void placeBid(User user, Auction auction, double amount) {
        if (amount >= auction.getStartingPrice()) {
            Bid bid = new Bid(user, amount);
            auction.addBid(bid);
        } else {
            System.out.println("Bid amount is lower than the starting price.");
        }
    }

    public void showAuctionDetails(Auction auction) {
        System.out.println("Auction Item: " + auction.getItem());
        System.out.println("Starting Price: " + auction.getStartingPrice());
        System.out.println("End Time: " + auction.getEndTime());
        auction.showBids();
        Bid highestBid = auction.getHighestBid();
        if (highestBid != null) {
            System.out.println("Highest Bid: " + highestBid.getAmount() + " by " + highestBid.getBidder().getName());
        } else {
            System.out.println("No bids placed yet.");
        }
    }
}

public class OnlineAuctionSystem {
    public static void main(String[] args) {
        AuctionSystem system = new AuctionSystem();

        // Register users
        User user1 = system.registerUser("Alice", "alice@example.com");
        User user2 = system.registerUser("Bob", "bob@example.com");

        // Create an auction
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10); // Auction ends in 10 minutes
        Auction auction = system.createAuction("Vintage Painting", 100.0, cal.getTime());

        // Users place bids
        system.placeBid(user1, auction, 120.0);
        system.placeBid(user2, auction, 150.0);

        // Show auction details
        system.showAuctionDetails(auction);
    }
}