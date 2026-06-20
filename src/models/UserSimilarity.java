
package models;

public class UserSimilarity implements Comparable<UserSimilarity> {
    private User user;
    private double similarityScore;

    public UserSimilarity(User user, double similarityScore) {
       this.user=user;
       this.similarityScore=similarityScore;
    }

    public User getUser() {
        return user;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }
    @Override
    public int compareTo(UserSimilarity other) {
        if (this.similarityScore > other.similarityScore) {
            return 1;   // Büyükse yığında yukarı çıksın
        } else if (this.similarityScore < other.similarityScore) {
            return -1;  // Küçükse aşağıda kalsın
        } else {
            return 0;   // Eşitse yerleri değişmesin
        }
    }
    
}
