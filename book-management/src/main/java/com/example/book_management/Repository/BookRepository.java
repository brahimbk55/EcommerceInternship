package com.example.book_management.Repository;

import com.example.book_management.Entities.Book;
import com.example.book_management.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {}
