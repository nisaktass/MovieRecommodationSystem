
package core;
import datastructures.CustomLinkedList;
import datastructures.MaxHeap;
import models.User;
import models.UserSimilarity;

public class RecommendationEngine {
    
    private double getAdjustedRating(int rawRating) {
        if (rawRating == 0) return 0.0; // Film hiç izlenmemişse etkisi 0 kalmalı
        
        switch (rawRating) {
            case 5: return 2.0;   // Çok olumlu
            case 4: return 1.0;   // Hafif olumlu
            case 3: return 0.0;   // Nötr (Hiçbir etkisi olmasın)
            case 2: return -1.0;  // Hafif olumsuz
            case 1: return -2.0;  // Çok olumsuz
            default: return 0.0;
        }
    }
    
   public double calculateCosineSimilarity(User userA, User userB, int totalMovieCount) {
    boolean hasCommonMovie = false;
    double dotProduct = 0.0;
    double normA = 0.0;
    double normB = 0.0;

    for (int i = 1; i <= totalMovieCount; i++) {
        int rA = userA.getRatingForMovie(i);
        int rB = userB.getRatingForMovie(i);

        // İki kullanıcı da filmi izlediyse
        if (rA > 0 && rB > 0) {
            hasCommonMovie = true;

           
            // Eğer puanlar zıt kutuplardaysa (biri 1-2, diğeri 4-5 ise) aradaki fark büyük olur.
            // Bu büyük farkı cezalandırmak için zıtlık çarpanı ekliyoruz.
            double ratingDifference = Math.abs(rA - rB);
            double penaltyFactor = 1.0;
            
            if (ratingDifference >= 3) {
                // Biri 5 biri 1 verdiyse fark 4 olur. Cezayı yapıştırıyoruz (Negatif etki)
                penaltyFactor = -1.5; 
            } else if (ratingDifference <= 1) {
                // Puanlar birbirine çok yakınsa (5'e 4 veya 1'e 2 gibi) ödüllendiriyoruz
                penaltyFactor = 1.5;
            }

            // Ham puanları koruyoruz ama cezayı/ödülü formüle yediriyoruz
            dotProduct += (rA * rB) * penaltyFactor;
            normA += rA * rA;
            normB += rB * rB;
        }
    }

    if (!hasCommonMovie) return 0.0;

    normA = Math.sqrt(normA);
    normB = Math.sqrt(normB);

    if (normA == 0 || normB == 0) return 0.0;

    return dotProduct / (normA * normB);
}
  public MaxHeap<UserSimilarity> buildHeapForTargetUser(User targetUser, CustomLinkedList<User> allUsers, int totalMovieCount) {
    MaxHeap<UserSimilarity> heap = new MaxHeap<>();
    CustomLinkedList.Node<User> current = allUsers.getHead();
    
    while (current != null) {
        User otherUser = current.data;
        if (otherUser.getId() != targetUser.getId()) {
            double similarity = calculateCosineSimilarity(targetUser, otherUser, totalMovieCount);
            
            // YENİ MANTIK: Benzerlik 0 değilse (yani pozitif veya negatif bir ilişki varsa) alıyoruz.
            // Math.abs(similarity) -> -0.8'i +0.8 yapar. Barajı geçenleri listeye dahil eder.
            if (Math.abs(similarity) > 0.1) { 
                UserSimilarity simResult = new UserSimilarity(otherUser, similarity);
                heap.insert(simResult); // Not: MaxHeap'in sıralama yaparken Math.abs(similarity) kullanması gerekebilir.
            }
        }
        current = current.next;
    }
    return heap;
}
}