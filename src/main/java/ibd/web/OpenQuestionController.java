package ibd.web;

import ibd.persistence.entity.OpenQuestion;
import ibd.service.OpenQuestionService;
import ibd.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/open-questions")
public class OpenQuestionController {

    @Autowired
    private OpenQuestionService openQuestionService;
    @Autowired
    private SubcategoryService subcategoryService;

    @RequestMapping("")
    public String listAll(Model model){
        model.addAttribute("questions", openQuestionService.findAll());
        return "open-questions/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("openQuestion", new OpenQuestion());
        model.addAttribute("subcategories", subcategoryService.findAll());
        return "open-questions/add";
    }

    @PostMapping("/add")
    public String postAdd(@Valid OpenQuestion question, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("subcategories", subcategoryService.findAll());
            return "open-questions/add";
        }
        openQuestionService.save(question);
        return "redirect:/open-questions";
    }

    @GetMapping("/delete")
    public String remove(@RequestParam Long id){
        openQuestionService.remove(id);
        return "redirect:/open-questions";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model){
        model.addAttribute("openQuestion", openQuestionService.findOne(id));
        model.addAttribute("subcategories", subcategoryService.findAll());
        return "open-questions/edit";
    }

    @PostMapping("/edit")
    public String postEdit(@Valid OpenQuestion question, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("subcategories", subcategoryService.findAll());
            return "open-questions/edit";
        }
        openQuestionService.save(question);
        return "redirect:/open-questions";
    }
}
