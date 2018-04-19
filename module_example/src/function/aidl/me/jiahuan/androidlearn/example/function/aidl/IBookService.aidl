package me.jiahuan.androidlearn.example.function.aidl;

import me.jiahuan.androidlearn.example.function.aidl.Book;


interface IBookService {
    List<Book> getBooks();

    void addBook(in Book book);
}



