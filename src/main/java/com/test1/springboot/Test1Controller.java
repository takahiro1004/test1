package com.test1.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import com.test1.springboot.repositories.MyDataTest1Repository;

@Controller
public class Test1Controller {

	@Autowired
	MyDataTest1Repository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") MyDataTest myDataTest, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "this is sample content.");
		mav.addObject("formModel", myDataTest);
		Iterable<MyDataTest> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}



	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView form(@ModelAttribute("formModel") @Validated MyDataTest myDataTest, BindingResult result,
			ModelAndView mav) {
		ModelAndView res = null;
		System.out.println(result.toString());
		if (!result.hasErrors()) {
			repository.saveAndFlush(myDataTest);
			res = new ModelAndView("redirect:/");
		} else {
			mav.setViewName("index");
			mav.addObject("msg", "sorry, error is occured...");
			Iterable<MyDataTest> list = repository.findAll();
			mav.addObject("datalist", list);
			res = mav;
		}
		return res;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute("formModel") MyDataTest myDataTest, @PathVariable int id,
			ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "edit mydata.");
		Optional<MyDataTest> data = repository.findById((Integer) id);
		mav.addObject("formModel", data.get());
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView update(@ModelAttribute MyDataTest myDataTest, ModelAndView mav) {
		repository.saveAndFlush(myDataTest);
		return new ModelAndView("redirect:/");
	}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id,
			ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title","delete mydata.");
		Optional<MyDataTest> data = repository.findById((Integer)id);
		mav.addObject("formModel",data.get());
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(@RequestParam Integer id, 
			ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}

}

//	@PostConstruct
//	public void init(){
//		MyDataTest d1 = new MyDataTest();
//		d1.setName("tuyano");
//		d1.setAge(123);
//		d1.setMail("syoda@tuyano.com");
//		d1.setMemo("this is my data!");
//		repository.saveAndFlush(d1);
//		MyDataTest d2 = new MyDataTest();
//		d2.setName("hanako");
//		d2.setAge(15);
//		d2.setMail("hanako@flower");
//		d2.setMemo("my girl friend.");
//		repository.saveAndFlush(d2);
//		MyDataTest d3 = new MyDataTest();
//		d3.setName("sachiko");
//		d3.setAge(37);
//		d3.setMail("sachico@happy");
//		d3.setMemo("my work friend...");
//		repository.saveAndFlush(d3);
//	}
//}