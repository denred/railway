package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.PaginationService;
import com.epam.redkin.railway.model.service.SearchService;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.validator.TrainValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainInfoCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;
    @Mock
    private AppContext appContext;
    @Mock
    private TrainValidator trainValidator;
    @Mock
    private SearchService searchService;
    @Mock
    private TrainService trainService;
    @Mock
    private PaginationService paginationService;
    @Mock
    private TrainInfoCommand command;
    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        command = new TrainInfoCommand(appContext);
        when(request.getSession()).thenReturn(session);
        when(appContext.getPaginationService()).thenReturn(paginationService);
        when(appContext.getTrainService()).thenReturn(trainService);
        when(appContext.getTrainValidator()).thenReturn(trainValidator);
        when(appContext.getSearchService()).thenReturn(searchService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void execute_validTrainFilter_expectRedirectToInfoTrainsPage() {
        // given
        String trainFilter = "1234";
        Map<String, String> search = new HashMap<>();
        search.put("number", trainFilter);
        Train train = Train.builder().number(trainFilter).build();
        int records = 1;
        List<Train> trainList = Collections.singletonList(train);

        // when
        when(searchService.getParameter(request, FILTER_TRAIN)).thenReturn("1234");
        when(trainService.getTrainListSize(anyMap())).thenReturn(records);
        when(trainService.getTrainListWithPagination(0, 5, new HashMap<>())).thenReturn(trainList);
        when(paginationService.getPage(request)).thenReturn(1);

        Router router = command.execute(request, response);

        assertEquals(Path.COMMAND_INFO_TRAINS_PAGE, router.getPagePath());
        assertEquals(Router.RouteType.REDIRECT, router.getRouteType());

        verify(request, times(1)).getSession();
        verify(request, times(0)).getParameter(FILTER_TRAIN);
        verify(session, times(0)).getAttribute(FILTER_TRAIN);
        verify(session, times(0)).setAttribute(FILTER_TRAIN, trainFilter);
        verify(session, times(1)).removeAttribute(ERROR_MESSAGE);
        verify(session, times(1)).setAttribute(TRAIN_LIST, trainList);
        verify(paginationService).setPaginationParameter(request, 1, records, 5, 5);
    }

}