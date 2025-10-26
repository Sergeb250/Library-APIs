package rw.ac.rca.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rw.ac.rca.library.entity.*;
import rw.ac.rca.library.repository.*;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (provinceRepository.count() > 0) {
            System.out.println("Location data already initialized. Skipping...");
            return; 
        }

        System.out.println("Initializing Rwanda administrative division data...");

        // Create Kigali City Province
        Province kigali = new Province();
        kigali.setName("Kigali City");
        kigali.setCode("KGL");
        kigali = provinceRepository.save(kigali);

        // Create Gasabo District
        District gasabo = new District();
        gasabo.setName("Gasabo");
        gasabo.setCode("GSB");
        gasabo.setProvince(kigali);
        gasabo = districtRepository.save(gasabo);

        // Create Remera Sector
        Sector remera = new Sector();
        remera.setName("Remera");
        remera.setCode("RMR");
        remera.setDistrict(gasabo);
        remera = sectorRepository.save(remera);

        // Create Rukiri I Cell
        Cell rukiriI = new Cell();
        rukiriI.setName("Rukiri I");
        rukiriI.setCode("RKR1");
        rukiriI.setSector(remera);
        rukiriI = cellRepository.save(rukiriI);

        // Create Villages in Rukiri I Cell
        Village village1 = new Village();
        village1.setName("Amahoro");
        village1.setCode("AMH");
        village1.setCell(rukiriI);
        villageRepository.save(village1);

        Village village2 = new Village();
        village2.setName("Ubumwe");
        village2.setCode("UBM");
        village2.setCell(rukiriI);
        villageRepository.save(village2);

        // Create Rukiri II Cell
        Cell rukiriII = new Cell();
        rukiriII.setName("Rukiri II");
        rukiriII.setCode("RKR2");
        rukiriII.setSector(remera);
        rukiriII = cellRepository.save(rukiriII);

        // Create Villages in Rukiri II Cell
        Village village3 = new Village();
        village3.setName("Gisozi");
        village3.setCode("GSZ");
        village3.setCell(rukiriII);
        villageRepository.save(village3);

        // Create Gisozi Sector
        Sector gisozi = new Sector();
        gisozi.setName("Gisozi");
        gisozi.setCode("GSZ");
        gisozi.setDistrict(gasabo);
        gisozi = sectorRepository.save(gisozi);

        // Create Akabahizi Cell
        Cell akabahizi = new Cell();
        akabahizi.setName("Akabahizi");
        akabahizi.setCode("AKB");
        akabahizi.setSector(gisozi);
        akabahizi = cellRepository.save(akabahizi);

        // Create Villages in Akabahizi Cell
        Village village4 = new Village();
        village4.setName("Kabuye");
        village4.setCode("KBY");
        village4.setCell(akabahizi);
        villageRepository.save(village4);

        // Create Kicukiro District
        District kicukiro = new District();
        kicukiro.setName("Kicukiro");
        kicukiro.setCode("KCK");
        kicukiro.setProvince(kigali);
        kicukiro = districtRepository.save(kicukiro);

        // Create Niboye Sector
        Sector niboye = new Sector();
        niboye.setName("Niboye");
        niboye.setCode("NBY");
        niboye.setDistrict(kicukiro);
        niboye = sectorRepository.save(niboye);

        // Create Niboye Cell
        Cell niboyeCell = new Cell();
        niboyeCell.setName("Niboye");
        niboyeCell.setCode("NBY");
        niboyeCell.setSector(niboye);
        niboyeCell = cellRepository.save(niboyeCell);

        // Create Villages in Niboye Cell
        Village village5 = new Village();
        village5.setName("Karama");
        village5.setCode("KRM");
        village5.setCell(niboyeCell);
        villageRepository.save(village5);

        Village village6 = new Village();
        village6.setName("Nyarutarama");
        village6.setCode("NYR");
        village6.setCell(niboyeCell);
        villageRepository.save(village6);

        // Create Eastern Province
        Province eastern = new Province();
        eastern.setName("Eastern Province");
        eastern.setCode("EST");
        eastern = provinceRepository.save(eastern);

        // Create Rwamagana District
        District rwamagana = new District();
        rwamagana.setName("Rwamagana");
        rwamagana.setCode("RWM");
        rwamagana.setProvince(eastern);
        rwamagana = districtRepository.save(rwamagana);

        // Create Muhazi Sector
        Sector muhazi = new Sector();
        muhazi.setName("Muhazi");
        muhazi.setCode("MHZ");
        muhazi.setDistrict(rwamagana);
        muhazi = sectorRepository.save(muhazi);

        // Create Gahini Cell
        Cell gahini = new Cell();
        gahini.setName("Gahini");
        gahini.setCode("GHN");
        gahini.setSector(muhazi);
        gahini = cellRepository.save(gahini);

        // Create Villages in Gahini Cell
        Village village7 = new Village();
        village7.setName("Nyarurama");
        village7.setCode("NYM");
        village7.setCell(gahini);
        villageRepository.save(village7);

        // Create Southern Province
        Province southern = new Province();
        southern.setName("Southern Province");
        southern.setCode("STH");
        southern = provinceRepository.save(southern);

        // Create Huye District
        District huye = new District();
        huye.setName("Huye");
        huye.setCode("HYE");
        huye.setProvince(southern);
        huye = districtRepository.save(huye);

        // Create Ngoma Sector
        Sector ngoma = new Sector();
        ngoma.setName("Ngoma");
        ngoma.setCode("NGM");
        ngoma.setDistrict(huye);
        ngoma = sectorRepository.save(ngoma);

        // Create Matyazo Cell
        Cell matyazo = new Cell();
        matyazo.setName("Matyazo");
        matyazo.setCode("MTZ");
        matyazo.setSector(ngoma);
        matyazo = cellRepository.save(matyazo);

        // Create Villages in Matyazo Cell
        Village village8 = new Village();
        village8.setName("Butare");
        village8.setCode("BTR");
        village8.setCell(matyazo);
        villageRepository.save(village8);

        System.out.println("Location data initialization completed!");
        System.out.println("Created: " + provinceRepository.count() + " provinces");
        System.out.println("Created: " + districtRepository.count() + " districts");
        System.out.println("Created: " + sectorRepository.count() + " sectors");
        System.out.println("Created: " + cellRepository.count() + " cells");
        System.out.println("Created: " + villageRepository.count() + " villages");

        // Initialize test users
        initializeUsers(village1, village2, village5);

        // Initialize test borrowers
        initializeBorrowers(village1, village3, village5, village6, village7);  

        // Initialize test books
        initializeBooks();

        // Initialize test borrow records
        initializeBorrowRecords();

        System.out.println("\n=== Data initialization completed successfully! ===");
        System.out.println("Users: " + userRepository.count());
        System.out.println("Borrowers: " + borrowerRepository.count());
        System.out.println("Books: " + bookRepository.count());
        System.out.println("Borrow Records: " + borrowRecordRepository.count());
    }

    private void initializeUsers(Village village1, Village village2, Village village5) {
        System.out.println("\nInitializing users...");

        // Admin user in Kigali
        User admin = new User();
        admin.setFirstName("Jean");
        admin.setLastName("Mukamana");
        admin.setEmail("jean.mukamana@library.rw");
        admin.setPhoneNumber("+250788123456");
        admin.setRole("ADMIN");
        admin.setVillage(village1);
        userRepository.save(admin);

        // Librarian in Kigali
        User librarian1 = new User();
        librarian1.setFirstName("Marie");
        librarian1.setLastName("Uwase");
        librarian1.setEmail("marie.uwase@library.rw");
        librarian1.setPhoneNumber("+250788234567");
        librarian1.setRole("LIBRARIAN");
        librarian1.setVillage(village2);
        userRepository.save(librarian1);

        // Librarian in Kicukiro
        User librarian2 = new User();
        librarian2.setFirstName("Patrick");
        librarian2.setLastName("Nkusi");
        librarian2.setEmail("patrick.nkusi@library.rw");
        librarian2.setPhoneNumber("+250788345678");
        librarian2.setRole("LIBRARIAN");
        librarian2.setVillage(village5);
        userRepository.save(librarian2);

        // Staff user
        User staff = new User();
        staff.setFirstName("Alice");
        staff.setLastName("Kamikazi");
        staff.setEmail("alice.kamikazi@library.rw");
        staff.setPhoneNumber("+250788456789");
        staff.setRole("STAFF");
        staff.setVillage(village1);
        userRepository.save(staff);

        System.out.println("Created " + userRepository.count() + " users");
    }

    private void initializeBorrowers(Village village1, Village village3, Village village5, 
                                      Village village6, Village village7) {
        System.out.println("\nInitializing borrowers...");

        // Borrower 1 - Kigali, Gasabo
        Borrower borrower1 = new Borrower();
        borrower1.setFirstName("Eric");
        borrower1.setLastName("Habimana");
        borrower1.setEmail("eric.habimana@gmail.com");
        borrower1.setPhoneNumber("+250788567890");
        borrower1.setMembershipNumber("LIB2024001");
        borrower1.setVillage(village1);
        borrowerRepository.save(borrower1);

        // Borrower 2 - Kigali, Gasabo
        Borrower borrower2 = new Borrower();
        borrower2.setFirstName("Grace");
        borrower2.setLastName("Uwamahoro");
        borrower2.setEmail("grace.uwamahoro@yahoo.com");
        borrower2.setPhoneNumber("+250788678901");
        borrower2.setMembershipNumber("LIB2024002");
        borrower2.setVillage(village3);
        borrowerRepository.save(borrower2);

        // Borrower 3 - Kigali, Kicukiro
        Borrower borrower3 = new Borrower();
        borrower3.setFirstName("David");
        borrower3.setLastName("Mugisha");
        borrower3.setEmail("david.mugisha@outlook.com");
        borrower3.setPhoneNumber("+250788789012");
        borrower3.setMembershipNumber("LIB2024003");
        borrower3.setVillage(village5);
        borrowerRepository.save(borrower3);

        // Borrower 4 - Kigali, Kicukiro
        Borrower borrower4 = new Borrower();
        borrower4.setFirstName("Sarah");
        borrower4.setLastName("Umutoni");
        borrower4.setEmail("sarah.umutoni@gmail.com");
        borrower4.setPhoneNumber("+250788890123");
        borrower4.setMembershipNumber("LIB2024004");
        borrower4.setVillage(village6);
        borrowerRepository.save(borrower4);

        // Borrower 5 - Eastern Province
        Borrower borrower5 = new Borrower();
        borrower5.setFirstName("Claude");
        borrower5.setLastName("Niyonzima");
        borrower5.setEmail("claude.niyonzima@gmail.com");
        borrower5.setPhoneNumber("+250788901234");
        borrower5.setMembershipNumber("LIB2024005");
        borrower5.setVillage(village7);
        borrowerRepository.save(borrower5);

        // Borrower 6 - Kigali
        Borrower borrower6 = new Borrower();
        borrower6.setFirstName("Diane");
        borrower6.setLastName("Iradukunda");
        borrower6.setEmail("diane.iradukunda@yahoo.com");
        borrower6.setPhoneNumber("+250789012345");
        borrower6.setMembershipNumber("LIB2024006");
        borrower6.setVillage(village1);
        borrowerRepository.save(borrower6);

        System.out.println("Created " + borrowerRepository.count() + " borrowers");
    }

    private void initializeBooks() {
        System.out.println("\nInitializing books...");

        // Fiction Books
        Book book1 = new Book();
        book1.setIsbn("978-0-7432-7356-5");
        book1.setTitle("The Kite Runner");
        book1.setAuthor("Khaled Hosseini");
        book1.setPublisher("Riverhead Books");
        book1.setPublicationYear(2003);
        book1.setCategory("Fiction");
        book1.setTotalCopies(5);
        book1.setAvailableCopies(3);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setIsbn("978-0-06-112008-4");
        book2.setTitle("To Kill a Mockingbird");
        book2.setAuthor("Harper Lee");
        book2.setPublisher("J.B. Lippincott & Co.");
        book2.setPublicationYear(1960);
        book2.setCategory("Fiction");
        book2.setTotalCopies(4);
        book2.setAvailableCopies(2);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setIsbn("978-0-452-28423-4");
        book3.setTitle("1984");
        book3.setAuthor("George Orwell");
        book3.setPublisher("Secker & Warburg");
        book3.setPublicationYear(1949);
        book3.setCategory("Fiction");
        book3.setTotalCopies(6);
        book3.setAvailableCopies(4);
        bookRepository.save(book3);

        // Non-Fiction Books
        Book book4 = new Book();
        book4.setIsbn("978-1-4165-4470-4");
        book4.setTitle("Sapiens: A Brief History of Humankind");
        book4.setAuthor("Yuval Noah Harari");
        book4.setPublisher("Harper");
        book4.setPublicationYear(2011);
        book4.setCategory("Non-Fiction");
        book4.setTotalCopies(5);
        book4.setAvailableCopies(3);
        bookRepository.save(book4);

        Book book5 = new Book();
        book5.setIsbn("978-0-385-49081-8");
        book5.setTitle("Educated");
        book5.setAuthor("Tara Westover");
        book5.setPublisher("Random House");
        book5.setPublicationYear(2018);
        book5.setCategory("Non-Fiction");
        book5.setTotalCopies(4);
        book5.setAvailableCopies(4);
        bookRepository.save(book5);

        // Science Books
        Book book6 = new Book();
        book6.setIsbn("978-0-553-10953-5");
        book6.setTitle("A Brief History of Time");
        book6.setAuthor("Stephen Hawking");
        book6.setPublisher("Bantam Books");
        book6.setPublicationYear(1988);
        book6.setCategory("Science");
        book6.setTotalCopies(3);
        book6.setAvailableCopies(2);
        bookRepository.save(book6);

        Book book7 = new Book();
        book7.setIsbn("978-0-307-88744-3");
        book7.setTitle("The Gene: An Intimate History");
        book7.setAuthor("Siddhartha Mukherjee");
        book7.setPublisher("Scribner");
        book7.setPublicationYear(2016);
        book7.setCategory("Science");
        book7.setTotalCopies(3);
        book7.setAvailableCopies(3);
        bookRepository.save(book7);

        // Technology Books
        Book book8 = new Book();
        book8.setIsbn("978-0-13-468599-1");
        book8.setTitle("Clean Code");
        book8.setAuthor("Robert C. Martin");
        book8.setPublisher("Prentice Hall");
        book8.setPublicationYear(2008);
        book8.setCategory("Technology");
        book8.setTotalCopies(5);
        book8.setAvailableCopies(3);
        bookRepository.save(book8);

        Book book9 = new Book();
        book9.setIsbn("978-0-201-63361-0");
        book9.setTitle("Design Patterns");
        book9.setAuthor("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
        book9.setPublisher("Addison-Wesley");
        book9.setPublicationYear(1994);
        book9.setCategory("Technology");
        book9.setTotalCopies(4);
        book9.setAvailableCopies(2);
        bookRepository.save(book9);

        // History Books
        Book book10 = new Book();
        book10.setIsbn("978-1-250-01021-5");
        book10.setTitle("We Wish to Inform You That Tomorrow We Will Be Killed With Our Families");
        book10.setAuthor("Philip Gourevitch");
        book10.setPublisher("Picador");
        book10.setPublicationYear(1998);
        book10.setCategory("History");
        book10.setTotalCopies(3);
        book10.setAvailableCopies(3);
        bookRepository.save(book10);

        System.out.println("Created " + bookRepository.count() + " books");
    }

    private void initializeBorrowRecords() {
        System.out.println("\nInitializing borrow records...");

        // Get test data
        Borrower borrower1 = borrowerRepository.findByMembershipNumber("LIB2024001").orElseThrow();
        Borrower borrower2 = borrowerRepository.findByMembershipNumber("LIB2024002").orElseThrow();
        Borrower borrower3 = borrowerRepository.findByMembershipNumber("LIB2024003").orElseThrow();
        Borrower borrower4 = borrowerRepository.findByMembershipNumber("LIB2024004").orElseThrow();
        
        Book book1 = bookRepository.findByIsbn("978-0-7432-7356-5").orElseThrow();
        Book book2 = bookRepository.findByIsbn("978-0-06-112008-4").orElseThrow();
        Book book3 = bookRepository.findByIsbn("978-0-452-28423-4").orElseThrow();
        Book book6 = bookRepository.findByIsbn("978-0-553-10953-5").orElseThrow();
        Book book8 = bookRepository.findByIsbn("978-0-13-468599-1").orElseThrow();
        Book book9 = bookRepository.findByIsbn("978-0-201-63361-0").orElseThrow();

        // Active borrow records
        BorrowRecord record1 = new BorrowRecord();
        record1.setBook(book1);
        record1.setBorrower(borrower1);
        record1.setBorrowDate(LocalDate.now().minusDays(10));
        record1.setDueDate(LocalDate.now().plusDays(4));
        record1.setStatus("BORROWED");
        record1.setNotes("First copy");
        borrowRecordRepository.save(record1);

        BorrowRecord record2 = new BorrowRecord();
        record2.setBook(book2);
        record2.setBorrower(borrower2);
        record2.setBorrowDate(LocalDate.now().minusDays(7));
        record2.setDueDate(LocalDate.now().plusDays(7));
        record2.setStatus("BORROWED");
        borrowRecordRepository.save(record2);

        BorrowRecord record3 = new BorrowRecord();
        record3.setBook(book8);
        record3.setBorrower(borrower3);
        record3.setBorrowDate(LocalDate.now().minusDays(5));
        record3.setDueDate(LocalDate.now().plusDays(9));
        record3.setStatus("BORROWED");
        borrowRecordRepository.save(record3);

        // Overdue borrow record
        BorrowRecord record4 = new BorrowRecord();
        record4.setBook(book6);
        record4.setBorrower(borrower4);
        record4.setBorrowDate(LocalDate.now().minusDays(20));
        record4.setDueDate(LocalDate.now().minusDays(6));
        record4.setStatus("BORROWED");
        record4.setNotes("Overdue - reminder sent");
        borrowRecordRepository.save(record4);

        // Returned records
        BorrowRecord record5 = new BorrowRecord();
        record5.setBook(book2);
        record5.setBorrower(borrower1);
        record5.setBorrowDate(LocalDate.now().minusDays(30));
        record5.setDueDate(LocalDate.now().minusDays(16));
        record5.setReturnDate(LocalDate.now().minusDays(18));
        record5.setStatus("RETURNED");
        borrowRecordRepository.save(record5);

        BorrowRecord record6 = new BorrowRecord();
        record6.setBook(book3);
        record6.setBorrower(borrower2);
        record6.setBorrowDate(LocalDate.now().minusDays(25));
        record6.setDueDate(LocalDate.now().minusDays(11));
        record6.setReturnDate(LocalDate.now().minusDays(10));
        record6.setStatus("RETURNED");
        borrowRecordRepository.save(record6);

        BorrowRecord record7 = new BorrowRecord();
        record7.setBook(book9);
        record7.setBorrower(borrower3);
        record7.setBorrowDate(LocalDate.now().minusDays(35));
        record7.setDueDate(LocalDate.now().minusDays(21));
        record7.setReturnDate(LocalDate.now().minusDays(19));
        record7.setStatus("RETURNED");
        record7.setNotes("Returned late - fine paid");
        borrowRecordRepository.save(record7);

        BorrowRecord record8 = new BorrowRecord();
        record8.setBook(book9);
        record8.setBorrower(borrower1);
        record8.setBorrowDate(LocalDate.now().minusDays(8));
        record8.setDueDate(LocalDate.now().plusDays(6));
        record8.setStatus("BORROWED");
        borrowRecordRepository.save(record8);

        System.out.println("Created " + borrowRecordRepository.count() + " borrow records");
    }
}
