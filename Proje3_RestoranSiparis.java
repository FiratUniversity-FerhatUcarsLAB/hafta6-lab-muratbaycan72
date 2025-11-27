import java.util.Scanner;

/**
 * Ad Soyad: Murat Baycan
 * Öğrenci No: 240541022
 * Proje: Proje 3 - Akıllı Restoran Sipariş Sistemi
 * Tarih: 27/11/2025
 */

public class Proje3_RestoranSiparis {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- RESTORAN MENÜSÜ ---");
        // Kullanıcı Seçimleri
        System.out.println("Ana Yemek (1:Tavuk 85, 2:Kebap 120, 3:Levrek 110, 4:Mantı 65, 0:Yok): ");
        int anaSecim = scan.nextInt();

        System.out.println("Başlangıç (1:Çorba 25, 2:Humus 45, 3:Börek 55, 0:Yok): ");
        int baslangicSecim = scan.nextInt();

        System.out.println("İçecek (1:Kola 15, 2:Ayran 12, 3:M.Suyu 35, 4:Limonata 25, 0:Yok): ");
        int icecekSecim = scan.nextInt();

        System.out.println("Tatlı (1:Künefe 65, 2:Baklava 55, 3:Sütlaç 35, 0:Yok): ");
        int tatliSecim = scan.nextInt();

        System.out.print("Saat (0-23): ");
        int saat = scan.nextInt();

        System.out.print("Öğrenci misiniz? (true/false): ");
        boolean ogrenci = scan.nextBoolean();
        
        System.out.print("Hafta içi mi? (true/false): ");
        boolean haftaIci = scan.nextBoolean();

        // Fiyatları Çek
        double anaFiyat = getMainDishPrice(anaSecim);
        double baslangicFiyat = getAppetizerPrice(baslangicSecim);
        double icecekFiyat = getDrinkPrice(icecekSecim);
        double tatliFiyat = getDessertPrice(tatliSecim);

        double hamToplam = anaFiyat + baslangicFiyat + icecekFiyat + tatliFiyat;

        // İndirim Mantığı
        boolean isCombo = isComboOrder((anaSecim > 0), (icecekSecim > 0), (tatliSecim > 0));
        boolean happyHour = isHappyHour(saat);
        
        // Bu metot içinde tüm indirimleri hesaplayıp düşüyoruz
        double odenecekTutar = calculateDiscount(hamToplam, isCombo, ogrenci && haftaIci, happyHour, icecekFiyat);
        double bahsis = calculateServiceTip(odenecekTutar);

        // Fiş Yazdırma
        System.out.println("\n--- SİPARİŞ FİŞİ ---");
        System.out.printf("Ara Toplam: %.2f TL\n", hamToplam);
        
        if(isCombo) System.out.println("- Combo Menü İndirimi (%15) uygulandı.");
        if(happyHour && icecekFiyat > 0) System.out.println("- Happy Hour İçecek İndirimi (%20) uygulandı.");
        if(ogrenci && haftaIci) System.out.println("- Öğrenci İndirimi (%10) uygulandı.");
        if(hamToplam > 200) System.out.println("- 200 TL Üzeri İndirim (%10) uygulandı."); // Ekstra kural

        System.out.printf("Ödenecek Tutar: %.2f TL\n", odenecekTutar);
        System.out.printf("Önerilen Bahşiş (%%10): %.2f TL\n", bahsis);
    }

    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0;
            case 2: return 120.0;
            case 3: return 110.0;
            case 4: return 65.0;
            default: return 0.0;
        }
    }

    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0;
            case 2: return 45.0;
            case 3: return 55.0;
            default: return 0.0;
        }
    }

    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0;
            case 2: return 12.0;
            case 3: return 35.0;
            case 4: return 25.0;
            default: return 0.0;
        }
    }

    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0;
            case 2: return 55.0;
            case 3: return 35.0;
            default: return 0.0;
        }
    }

    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return anaVar && icecekVar && tatliVar;
    }

    public static boolean isHappyHour(int saat) {
        return saat >= 14 && saat <= 17;
    }

    // İndirimlerin sırayla uygulanması
    // Not: Java'da method imzası esnektir, içecek fiyatını happy hour için buraya parametre ekledim.
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, boolean happyHour, double icecekFiyat) {
        double indirimliTutar = tutar;

        // 1. Combo İndirimi (%15 tüm tutardan)
        if (combo) {
            indirimliTutar -= (tutar * 0.15);
        }

        // 2. Happy Hour (%20 sadece içecekten)
        if (happyHour && icecekFiyat > 0) {
            indirimliTutar -= (icecekFiyat * 0.20);
        }

        // 3. 200 TL Üzeri İndirim (Ham tutara bakılır genelde, %10)
        // Eğer ödev metninde sıralama belirtilmemişse, genellikle bu en sonda uygulanır.
        // Burada test senaryosunu tutturmak için öğrenci indiriminden önce uyguluyorum.
        
        // Not: Test senaryosunda "200 TL üzeri" indirimi açıkça hesaplanmamış ama kurallarda var.
        // Test senaryosundaki math'ı bozmamak için burayı yoruma alabilirsin ama kuralda var.
        // Ben kurala uyup ekliyorum:
        if (tutar > 200) {
             indirimliTutar -= (indirimliTutar * 0.10);
        }

        // 4. Öğrenci İndirimi (%10 kalan tutardan)
        if (ogrenci) {
            indirimliTutar -= (indirimliTutar * 0.10);
        }

        return indirimliTutar;
    }

    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }
}
