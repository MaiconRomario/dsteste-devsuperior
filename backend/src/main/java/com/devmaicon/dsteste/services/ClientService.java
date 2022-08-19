package com.devmaicon.dsteste.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devmaicon.dsteste.dto.ClientDTO;
import com.devmaicon.dsteste.entities.Client;
import com.devmaicon.dsteste.repositories.ClientRepository;
import com.devmaicon.dsteste.services.exceptions.DatabaseException;
import com.devmaicon.dsteste.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list =	repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.get();
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
		Client entity = repository.getOne(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity); 
		
		}
	catch (EntityNotFoundException e) {
		throw new ResourceNotFoundException("Id não encontrado "+ id);
	}
}

public void delete(Long id) {
	 try {
		 repository.deleteById(id);
	 }
	 catch (EmptyResultDataAccessException e) {
		throw new ResourceNotFoundException("Id não encontrado "+ id);
	}
	 catch (DataIntegrityViolationException e) {
		throw new DatabaseException("Violação da integridade");
	 }	
		
	}
	
	
	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
		
		
	}

}
