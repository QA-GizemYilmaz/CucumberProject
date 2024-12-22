Feature: Hepsiburada UI Test
  @UI
  Scenario: Kullanıcı ürün ekleme senaryosu
    Given Kullanıcı "https://www.hepsiburada.com/" adresine gider
    And Kullanıcı çerezleri kabul eder
    When Kullanıcı "Elektronik" -> "Bilgisayar/Tablet" -> "Apple" kategorisine gider
    And Filtrelerden "Ekran Boyutu->13,2 inç" seçer
    And Kullanıcı çıkan sonuçlardan en yüksek fiyatlı ürüne tıklar
    And Kullanıcı açılan ürün detay sayfasındaki "Sepete ekle" butonuna tıklar
    Then Ürünün sepete eklendiğini ve fiyatının ürün detay sayfasıyla aynı olduğunu doğrular
