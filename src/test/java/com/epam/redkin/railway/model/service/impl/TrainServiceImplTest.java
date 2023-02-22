package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.repository.TrainRepository;
import com.epam.redkin.railway.model.service.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainServiceImplTest {
    private TrainRepository trainRepository;
    private TrainService trainService;

    @BeforeEach
    public void setUp() {
        trainRepository = mock(TrainRepository.class);
        trainService = new TrainServiceImpl(trainRepository);
    }

    @Test
    void getTrainById() {
        Train train = Train.builder().id(1).number("0123").build();
        when(trainRepository.getById(1)).thenReturn(train);
        assertEquals(train, trainService.getTrainById(1));
        verify(trainRepository, times(1)).getById(1);
    }

    @Test
    void getTrainByIdThrowsException() {
        when(trainRepository.getById(1)).thenThrow(new DataBaseException("Error"));
        assertThrows(ServiceException.class, () -> trainService.getTrainById(1));
        verify(trainRepository, times(1)).getById(1);
    }

    @Test
    void getTrainListWithPagination() {
        Map<String, String> search = new HashMap<>();
        search.put("number", "123");
        List<Train> trainList = List.of(Train.builder().number("1").build(), Train.builder().number("17").build());
        when(trainRepository.getTrainListWithPagination(1, 5, search)).thenReturn(trainList);
        assertArrayEquals(trainList.toArray(), trainRepository.getTrainListWithPagination(1, 5, search).toArray());
        verify(trainRepository, times(1)).getTrainListWithPagination(1, 5, search);
    }

    @Test
    void getTrainListWithPaginationThrowsException() {
        Map<String, String> search = new HashMap<>();
        search.put("number", "123");
        when(trainRepository.getTrainListWithPagination(1, 5, search)).thenThrow(new DataBaseException("Error"));
        assertThrows(ServiceException.class, () -> trainRepository.getTrainListWithPagination(1, 5, search));
        verify(trainRepository, times(1)).getTrainListWithPagination(1, 5, search);
    }

    @Test
    public void testGetTrainListSize() throws DataBaseException {
        Map<String, String> search = new HashMap<>();
        search.put("number", "123");
        when(trainRepository.getTrainListSize(search)).thenReturn(10);
        int result = trainService.getTrainListSize(search);
        assertEquals(10, result);
        verify(trainRepository, times(1)).getTrainListSize(search);
    }

    @Test
    public void testGetTrainListSizeThrowsException() throws DataBaseException {
        Map<String, String> search = new HashMap<>();
        search.put("number", "123");
        when(trainRepository.getTrainListSize(search)).thenThrow(new DataBaseException("Error"));
        assertThrows(ServiceException.class, () -> trainService.getTrainListSize(search));
        verify(trainRepository, times(1)).getTrainListSize(search);
    }


    @Test
    void updateTrain() {
        Train train = Train.builder().id(1).number("0123").build();
        when(trainRepository.update(train)).thenReturn(true);
        trainService.updateTrain(train);
        verify(trainRepository, times(1)).update(train);
    }

    @Test
    void updateTrainThrowsException() {
        Train train = Train.builder().id(1).number("0123").build();
        when(trainRepository.update(train)).thenThrow(new DataBaseException("Error"));
        assertThrows(ServiceException.class, () -> trainService.updateTrain(train));
        verify(trainRepository, times(1)).update(train);
    }


    @Test
    void addTrain() {
        Train train = Train.builder().id(1).number("0123").build();
        when(trainRepository.create(train)).thenReturn(1);
        trainService.addTrain(train);
        verify(trainRepository, times(1)).create(train);
    }

    @Test
    void addTrainThrowsException() {
        Train train = Train.builder().id(1).number("0123").build();
        when(trainRepository.create(train)).thenThrow(new DataBaseException("Error"));
        assertThrows(ServiceException.class, () -> trainService.addTrain(train));
        verify(trainRepository, times(1)).create(train);
    }

    @Test
    void getTrainList() {
        List<Train> trainList = List.of(Train.builder().number("1").build(), Train.builder().number("17").build());
        when(trainRepository.getTrainList()).thenReturn(trainList);
        assertArrayEquals(trainList.toArray(), trainRepository.getTrainList().toArray());
        verify(trainRepository, times(1)).getTrainList();
    }

    @Test
    void getTrainListThrowsException() {
        List<Train> trainList = List.of(Train.builder().number("1").build(), Train.builder().number("17").build());
        when(trainRepository.getTrainList()).thenThrow(new DataBaseException("Error"));
        assertThrows(ServiceException.class, () -> trainRepository.getTrainList());
        verify(trainRepository, times(1)).getTrainList();
    }

    @Test
    void removeTrain() {
        doNothing().when(trainRepository).delete(1);
        trainService.removeTrain(1);
        verify(trainRepository, times(1)).delete(1);
    }
}