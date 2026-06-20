
package Main;

import core.DataManager;
import core.RecommendationEngine;
import datastructures.CustomLinkedList;
import datastructures.MaxHeap;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import models.User;
import models.UserSimilarity;

public class Main {

    public static void main(String[] args) {
        System.out.println("System start");
        DataManager dataManager = new DataManager();
        RecommendationEngine engine = new RecommendationEngine();

        String moviesPath = "movies.csv";
        String mainDataPath = "main_data.csv";
        String targetUsersPath = "target_user.csv";
        System.out.println("Dosyalar yukleniyor, lutfen bekleyin...");
        dataManager.loadMovies(moviesPath);
        dataManager.loadMainData(mainDataPath);
        dataManager.loadTargetUsers(targetUsersPath);

        CustomLinkedList<User> allUsers = dataManager.getAllUsers();
        CustomLinkedList<Integer> targetIds = dataManager.getTargetUserIds();
        int totalMovieCount = dataManager.getMovieCount();

        System.out.println("------------------------------------------------");
        System.out.println("Toplam Yuklenen Kullanici Sayisi: " + allUsers.getSize());
        System.out.println("Hedef Kullanici Listesi Boyutu: " + targetIds.getSize());
        System.out.println("------------------------------------------------");

        if (targetIds == null || targetIds.getSize() == 0) {
            System.out.println("HATA: target_user.csv icinde hic hedef id bulunamadi!");
            return;
        }
        int firstTargetId = targetIds.getHead().data;
        User targetUser = null;
        CustomLinkedList.Node<User> current = allUsers.getHead();
        while (current != null) {
            if (current.data.getId() == firstTargetId) {
                targetUser = current.data;
                break;
            }
            current = current.next;
        }
        if (targetUser == null && allUsers.getHead() != null) {
            targetUser = allUsers.getHead().data;
        }
        if(targetUser == null){
            System.out.println("Hedef Kullanici bulunamadi.");
            return;
        }
        System.out.println("Hedef Kullanici ID: " + targetUser.getId() + " icin Max-Heap insa ediliyor...");
        
        MaxHeap<UserSimilarity> similarityHeap = engine.buildHeapForTargetUser(targetUser, allUsers, totalMovieCount);
        System.out.println("Max-Heap basariyla kuruldu. Toplam komsu sayisi: " + similarityHeap.getSize());
        System.out.println("------------------------------------------------");
        int X = 5;
        System.out.println("HEDEF KULLANICIYA EN BENZER " + X + " KULLANICI (MAX-HEAP EXTRACT):");
        for (int i = 1; i <= X; i++) {
            if (similarityHeap.isEmpty()) {
                break;
            }
            UserSimilarity topSim = similarityHeap.extractMax();
            System.out.println(i + ". Komsu -> Kullanici ID: " + topSim.getUser().getId()
                                + " | Benzerlik Skoru: " + topSim.getSimilarityScore());
        }
        System.out.println("\n=== TEST BASARIYLA TAMAMLANDI ===");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.out.println("Sistem teması yüklenemedi, varsayılan tema kullanılıyor.");
                }
                System.out.println("=== FILM ONERI SISTEMI PROJESI BASLATILIYOR ===");
                    gui.FirstPnl anaEkran = new gui.FirstPnl();
                    anaEkran.setVisible(true);
            }
        });
    }
}
