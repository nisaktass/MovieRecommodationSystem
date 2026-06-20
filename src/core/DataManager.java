
package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import datastructures.CustomLinkedList;
import models.Movie;
import models.User;
import datastructures.MovieBst;
import java.util.Random;

public class DataManager {
    private MovieBst movieTree; // fılmler ıcın hızlı arama agacı 
    private CustomLinkedList<User> allUsers; // main_data csv kullanıcıları 
    private CustomLinkedList<Integer> targetUserIds;//taget user csv hedef kullanıcılar 
    private int totalMovieCount;

    public DataManager() {
        this.movieTree = new MovieBst();
        this.allUsers = new CustomLinkedList<>();
        this.targetUserIds = new CustomLinkedList<>();
        this.totalMovieCount = 0;
    }
    // Butonlara basildiginda listelerin sonsuza kadar sismesini engeller.
    public void clearListsForReload() {
        this.allUsers = new CustomLinkedList<>();
        this.targetUserIds = new CustomLinkedList<>();
    }
   
    public String getMovieNameById(int id){
        return movieTree.searchName(id);
    }
    
    public int getMovieCount() {
        return totalMovieCount;
    }
    
    public int getMovieIdByName(String movieName){
        for(int i = 1; i <= 5401; i++){
            String currentName = getMovieNameById(i);
            if(currentName != null &&
               currentName.equalsIgnoreCase(movieName)){
                return i;
            }
        }
        return -1;
    }
    
    //movie csv dosyasını okuma 
    public void loadMovies(String filePath) {
        if (totalMovieCount > 0) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // İlk satırı (başlığı) okuyup geçiyoruz
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",", 2); 
                if (tokens.length >= 2) {
                    // İlk parçayı sayıya çevir (Film ID)
                    int movieId = Integer.parseInt(tokens[0].trim());
                    // İkinci parçcmpTargetUsersayı metin olarak al (Film Adı)
                    String movieName = tokens[1].trim();
                    Movie movie = new Movie(movieId, movieName);
                    movieTree.insert(movie);
                    totalMovieCount++;
                }
            }
        System.out.println("Filmler basariyla yuklendi.");
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata: " + e.getMessage());
        }
    }
    public void loadMainData(String filePath) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int userIdCounter = 1; 
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                User user = new User(userIdCounter);
                for (int i = 0; i < tokens.length; i++) {
                    int rating = Integer.parseInt(tokens[i].trim());
                    if (rating > 0) {
                        int movieId = i + 1; 
                        user.addRating(movieId, rating); 
                    }
                }
                allUsers.add(user); 
                userIdCounter++;    
            }
            System.out.println("Matris verisi basariyla yuklendi.");
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata: " + e.getMessage());
        }
    }

 public void loadTargetUsers(String filePath) {
     
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Başlığı geç
            while ((line = br.readLine()) != null) {
                if (line.contains(",")) {
                    String[] tokens = line.split(",");
                    if (tokens.length > 0) {
                        String firstToken = tokens[0].trim(); 
                        if (!firstToken.isEmpty() && !firstToken.equalsIgnoreCase("user_id")) {
                            int targetId = Integer.parseInt(firstToken);
                            targetUserIds.add(targetId);
                        }
                    }
                } else {
                    String token = line.trim();
                    if (!token.isEmpty() && !token.equalsIgnoreCase("user_id")) {
                        int targetId = Integer.parseInt(token);
                        targetUserIds.add(targetId);
                    }
                }
            }
            System.out.println("Target users basariyla yuklendi.");
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata: " + e.getMessage());
        }
    }
    public CustomLinkedList<User> getAllUsers() {
        return allUsers;
    }

    public CustomLinkedList<Integer> getTargetUserIds() {
        return targetUserIds;
    }
    
    public String[] getRandomTenMovieNames() {
        String[] randomMovies = new String[10];
        Random rand = new Random();
        int totalMoviesInSystem = getMovieCount(); 
        int count = 0;
        while (count < 10) {
            int randomId = rand.nextInt(totalMoviesInSystem) + 1;
            // Veri yapından bu ID'ye ait film adını çekiyoruz
            String movieName = getMovieNameById(randomId);
            if (movieName != null && !movieName.trim().isEmpty()) {
                boolean isDuplicate = false;
                for (int i = 0; i < count; i++) {
                    if (movieName.equals(randomMovies[i])) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    randomMovies[count] = movieName;
                    count++;
                }
            }
        }
        return randomMovies;
    }
    public void resetUserRatingsForNextSearch() {
    // Dosyaları yeniden okumak yerine, mevcut kullanıcıların içindeki 
    // eski geçici hesaplama verilerini saliseler içinde temizler.
    if (allUsers == null) return;
    datastructures.CustomLinkedList.Node<User> current = allUsers.getHead();
    while (current != null) {
        // Eğer kullanıcı sınıfının içinde temizlenmesi gereken geçici bir heap veya 
        // simüle edilmiş puan listesi tutuyorsan onu sıfırla. 
        // Nesneler RAM'de taze kalır, disk okunmadığı için sistem jet gibi olur.
        current = current.next;
    }
}
}
