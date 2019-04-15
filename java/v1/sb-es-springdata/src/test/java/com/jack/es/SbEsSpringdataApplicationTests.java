package com.jack.es;

import com.jack.es.po.Book;
import com.jack.es.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbEsSpringdataApplicationTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testIndex() {
        Book book = new Book();
        book.setId(1);
        book.setName("BookName");
        book.setAuthor("Jack");

        bookRepository.index(book);
    }

    @Test
    public void testSearch() {
        List<Book> list = bookRepository.findByNameLike("Boo");

        for (Book book: list) {
            System.out.println(book);
        }
    }

}
