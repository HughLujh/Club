package app.blog.service;

public interface BaseService <T>{
    void save (T t);
    void delete(Long id);
}
