    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package edu.dwes.pi_manuelRetamosa_backend.services;

    import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CreditCardDTO;
    import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICreditCardRepository;
    import edu.dwes.pi_manuelRetamosa_backend.models.entities.CreditCard;
    import jakarta.transaction.Transactional;
    import java.util.ArrayList;
    import java.util.List;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    /**
     *
     * @author manue
     */
    @Service
    public class CreditCardService {

        @Autowired
        private ICreditCardRepository creditCardRepository;

        @Autowired
        private ConverterDTO converterDTO;

        public List<CreditCardDTO> findAll(){
            List<CreditCard> list =(List<CreditCard>)creditCardRepository.findAll();
            List<CreditCardDTO> listDTOs=new ArrayList<>();
            for(CreditCard c:list){
                listDTOs.add(converterDTO.convADTO(c));
            }

            return listDTOs;
        }

        public CreditCardDTO findById(Long id){
            CreditCard c= creditCardRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarjeta de crediro no encontrada"));
            return converterDTO.convADTO(c);
        }

        public CreditCardDTO save(CreditCardDTO creditCardDTO){
            CreditCard creditCard=converterDTO.convAEntidad(creditCardDTO);
            CreditCard saved= creditCardRepository.save(creditCard);
            return converterDTO.convADTO(saved);
        }

        @Transactional
        public void delete(Long id){
            creditCardRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarjeta de crediro no encontrada"));
            creditCardRepository.deleteById(id);
        }

        public List<CreditCardDTO> findByUser(Long userId) {
            List<CreditCard> listEntities = creditCardRepository.findByUserId(userId);
            List<CreditCardDTO> listDTOs = new ArrayList<>();
            for (CreditCard c : listEntities) {
                listDTOs.add(converterDTO.convADTO(c));
            }
            return listDTOs;
        }
    }
