import random

def sayi_tahmin_oyunu():
    """
    1 ile 100 arasında rastgele seçilen bir sayıyı tahmin etmeye çalıştığınız basit bir oyun.
    """
    print("=== SAYI TAHMİN OYUNUNA HOŞ GELDİNİZ ===")
    print("1 ile 100 arasında bir sayı tuttum. Tahmin etmeye çalış!")
    
    # Rastgele sayı üret (1-100 dahil)
    hedef_sayi = random.randint(1, 100)
    tahmin_sayisi = 0
    tahmin_hakki = 10  # Kullanıcıya 10 tahmin hakkı ver
    
    while tahmin_hakki > 0:
        tahmin_hakki -= 1
        tahmin_sayisi += 1
        
        try:
            tahmin = int(input(f"\nTahmininiz (Kalan hak: {tahmin_hakki + 1}): "))
        except ValueError:
            print("Lütfen geçerli bir sayı girin!")
            continue
        
        if tahmin < hedef_sayi:
            print("Daha YÜKSEK bir sayı dene!")
        elif tahmin > hedef_sayi:
            print("Daha DÜŞÜK bir sayı dene!")
        else:
            print(f"TEBRİKLER! {tahmin_sayisi} denemede doğru tahmin ettiniz!")
            print(f"Sayı: {hedef_sayi}")
            break
        
        if tahmin_hakki == 0:
            print(f"\nÜzgünüm, tahmin hakkınız bitti! Doğru sayı: {hedef_sayi}")
    
    # Oyun sonu
    tekrar_oyna = input("\nTekrar oynamak ister misiniz? (E/H): ").strip().upper()
    if tekrar_oyna == "E":
        sayi_tahmin_oyunu()
    else:
        print("Oyunu oynadığınız için teşekkürler! Güle güle!")

if __name__ == "__main__":
    sayi_tahmin_oyunu()