package org.springframework.samples.mvc.basic.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	private Map<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new Account());
		return "account/createForm";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String create(@Valid Account account, BindingResult result) {
		if (result.hasErrors()) {
			return "account/createForm";
		}
		this.accounts.put(account.assignId(), account);
		return "redirect:/account/" + account.getId();
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String getView(@PathVariable Long id, Model model) {
		Account account = this.accounts.get(id);
		if (account == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute(account);
		return "account/view";
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String updateVew(@PathVariable Long id, Model model) {
		Account account = this.accounts.get(id);
		if (account == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute("account",account);
		return "account/edit";
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.POST)
	public String updateView(@PathVariable Long id,@ModelAttribute("account") Account account) {
		if (account == null) {
			throw new ResourceNotFoundException(id);
		}
		
		if( !accounts.containsKey(id) ) 
			throw new ResourceNotFoundException(id);
	    account.setId(id);
		this.accounts.put(id, account);
		return "redirect:/account/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public String finalView(@ModelAttribute("account") Account account, Model model) {
		List<Account>al = new ArrayList<Account>();
		
		for(Long key: sortHashMap(accounts)){
			al.add(this.accounts.get(key));
		}
		model.addAttribute("account", al);
        return "account/list";
    }
	
	@RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
    public String deleteView(@PathVariable Long id, Account account) {
		if (this.accounts.get(id) == null) {
			throw new ResourceNotFoundException(id);
		}
		
		account = this.accounts.get(id);
		this.accounts.remove(account.getId());
        return "account/delete";
    }
	
	public List<Long> sortHashMap(Map map){
		List<Long>sortedKeys = new ArrayList<Long>(map.keySet());
		Collections.sort(sortedKeys);
		return sortedKeys;
	}
}
