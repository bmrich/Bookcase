package com.rich.bryan;

import com.rich.bryan.dao.Impl.DaoService;
import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.entity.Book;
import com.rich.bryan.services.ShelvesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadingListDemo1ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String check = "9780671695348";

	@Test
	public void newPassword(){
		System.out.println(passwordEncoder.encode("test"));
	}

	@Autowired
	private DaoService daoService;

	@Test
	public void testQuery(){
//		List<Book> books = daoService.getAuthor(36L, "bryanrich3");
		List<Book> books = daoService.getBooks("bryanrich3");
//		Book book = daoService.getSingleBook("9780060888763");
//		System.out.println(book);

		for (Book book: books){
			System.out.println(book);
		}

//		daoService.deleteBook(56L,"miles");
	}

	@Autowired
	private ShelvesService shelvesDao;

	@Test
	public void shelves(){
//		shelvesDao.createShelf("bryanrich3", "Mystery");
//		shelvesDao.addBooktoShelf("miles", "War", 96L);
		List<String> shelves = shelvesDao.getShelves("bryanrich3");
		for(String shelf: shelves){
			System.out.println(shelf);
		}
	}

}
