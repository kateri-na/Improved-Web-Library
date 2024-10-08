package ru.panina.springproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.panina.springproject.models.Book;
import ru.panina.springproject.models.Person;
import ru.panina.springproject.services.BooksService;
import ru.panina.springproject.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer books_per_page,
                        @RequestParam(name = "sort_by_year", required = false) String sort_by_year) {

        if(page != null && books_per_page != null && sort_by_year != null) {
            model.addAttribute("books", booksService.findAll(page, books_per_page, sort_by_year));
        } else if (page != null && books_per_page != null && sort_by_year == null) {
            model.addAttribute("books", booksService.findAll(page, books_per_page));
        } else if (sort_by_year != null && page == null && books_per_page == null) {
            model.addAttribute("books", booksService.findAll(sort_by_year));
        } else {
            model.addAttribute("books", booksService.findAll());
        }
        return "books/index";
    }
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findById(id));

        Person owner = booksService.findOwner(id);
        if (owner == null){
            model.addAttribute("people", peopleService.findAll());
        }
        else
            model.addAttribute("owner", owner);

        return "books/show";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/appoint")
    public String appoint(@PathVariable("id") int id, @ModelAttribute Person person, @ModelAttribute Book book) {
        booksService.appoint(person, id);
        return "redirect:/books/{id}";
    }
    @PatchMapping("{id}/free")
    public String free(@PathVariable("id") int id, @ModelAttribute Book book) {
        booksService.free(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false) String query, Model model) {
        if(query != null) {
            model.addAttribute("foundBooks", booksService.findAllByTitle(query));
        }
        return "books/search";
    }
}
