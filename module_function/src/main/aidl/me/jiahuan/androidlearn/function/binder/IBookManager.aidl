// IBookManager.aidl
package me.jiahuan.androidlearn.function.binder;

import me.jiahuan.androidlearn.function.binder.Book;

interface IBookManager {
// 定义方法
List<Book> getBookList();
void addBook(in Book book);
}
