package vananh.work;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import vananh.work.entity.Work;
import vananh.work.service.WorkService;
import vananh.work.util.constant.ErrorCode;
import vananh.work.util.provider.StatusProvider;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WorkControllerTest
 */
@WebMvcTest
public class WorkControllerTest {

    private static final String API_URL = "/work/";

    private static final String UTF_8 = "UTF-8";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkService workService;

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Test API get Work by name
     *
     * @throws Exception
     */
    @Test
    public void testGetWorkByName() throws Exception {
        // Setting data for work
        Work work = new Work();
        work.setWorkName("Work_01");
        work.setStartingDate(Date.valueOf("2020-01-10"));
        work.setEndingDate(Date.valueOf("2020-01-11"));
        work.setStatus("Planning");

        // Testing
        Mockito.when(workService.getByName("Work_01")).thenReturn(Optional.of(work));

        // Case 1: success
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_URL + "{name}", "Work_01")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workName", Matchers.equalTo("Work_01")))
                .andExpect(jsonPath("$.startingDate", Matchers.equalTo("2020-01-10")))
                .andExpect(jsonPath("$.endingDate", Matchers.equalTo("2020-01-11")))
                .andExpect(jsonPath("$.status", Matchers.equalTo(StatusProvider.Planning.toString())));

        // Case 2: fail
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_URL + "{name}", "Work_02")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", Matchers.equalTo(ErrorCode.FIND_NOT_FOUND_CODE)))
                .andExpect(jsonPath("$.message", Matchers.equalTo("Work is not found with WorkName 'Work_02'")));
    }

    /**
     * Test API get all work (have paging and sorting)
     *
     * @throws Exception
     */
    @Test
    public void testGetAllWork() throws Exception {
        // List work
        List<Work> workList = new ArrayList<>();

        Work work1= new Work();
        work1.setWorkName("Work_01");
        work1.setStartingDate(Date.valueOf("2020-01-10"));
        work1.setEndingDate(Date.valueOf("2020-02-11"));
        work1.setStatus("Planning");

        Work work2= new Work();
        work1.setWorkName("Work_02");
        work1.setStartingDate(Date.valueOf("2020-01-09"));
        work1.setEndingDate(Date.valueOf("2020-01-11"));
        work1.setStatus("Planning");

        Work work3= new Work();
        work1.setWorkName("Work_03");
        work1.setStartingDate(Date.valueOf("2020-01-10"));
        work1.setEndingDate(Date.valueOf("2020-03-11"));
        work1.setStatus("Planning");

        // Add data for workList
        workList.add(work1);
        workList.add(work2);
        workList.add(work3);

        // Paging information
        int page = 0;
        int size = 10;
        Sort sort = Sort.by("startingDate").ascending().and(Sort.by("endingDate").descending());
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Work> workPage = new PageImpl<>(workList, pageable, workList.size());

        // Check Paging and Sorting
        Mockito.when(workService.findAll(pageable)).thenReturn(workPage);
        Page<Work> works = workService.findAll(pageable);
        assertEquals(works.getNumberOfElements(), 3);
        PageableAssert.assertThat(pageable).hasPageNumber(0);
        PageableAssert.assertThat(pageable).hasPageSize(10);
        PageableAssert.assertThat(pageable).hasSort("startingDate", Sort.Direction.ASC);
        PageableAssert.assertThat(pageable).hasSort("endingDate", Sort.Direction.DESC);

        // Check data
        mockMvc.perform(get(API_URL + "?size=10&page=0&sort=startingDate,asc&sort=endingDate,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.content[0].workName", Matchers.equalTo("Work_03")))
                .andExpect(jsonPath("$.content[0].startingDate", Matchers.equalTo("2020-01-10")))
                .andExpect(jsonPath("$.content[0].endingDate", Matchers.equalTo("2020-03-11")))
                .andExpect(jsonPath("$.content[0].status", Matchers.equalTo(StatusProvider.Planning.toString())));
    }

    /**
     * Test API add Work
     *
     * @throws Exception
     */
    @Test
    public void testAddNewWork() throws Exception {
        // Setting data for work
        Work work = new Work();
        work.setWorkName("Work_01");
        work.setStartingDate(Date.valueOf("2020-01-10"));
        work.setEndingDate(Date.valueOf("2020-01-11"));
        work.setStatus("Planning");

        Mockito.when(workService.save(ArgumentMatchers.any())).thenReturn(work);
        String json = mapper.writeValueAsString(work);
        // Case 1: success
        mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.workName", Matchers.equalTo("Work_01")))
                .andExpect(jsonPath("$.startingDate", Matchers.equalTo("2020-01-10")))
                .andExpect(jsonPath("$.endingDate", Matchers.equalTo("2020-01-11")))
                .andExpect(jsonPath("$.status", Matchers.equalTo(StatusProvider.Planning.toString())));

        // Case 2: validate argument
        // Case 2-1: workName is invalid (workName is must be requid)
        work.setWorkName("");
        json = mapper.writeValueAsString(work);
        Mockito.when(workService.save(ArgumentMatchers.any())).thenReturn(work);
        mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", Matchers.equalTo(ErrorCode.ARGUMENT_NOT_INVALID_CODE)))
                .andExpect(jsonPath("$.errors.workName", Matchers.equalTo("WorkName must be requid.")));

        // Case 2-2: status is invalid (value of status must be: "Planning" or "Doing" or "Complete")
        work.setStatus("Planning2");
        json = mapper.writeValueAsString(work);
        Mockito.when(workService.save(ArgumentMatchers.any())).thenReturn(work);
        mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", Matchers.equalTo(ErrorCode.ARGUMENT_NOT_INVALID_CODE)))
                .andExpect(jsonPath("$.errors.workName", Matchers.equalTo("WorkName must be requid.")))
                .andExpect(jsonPath("$.errors.status", Matchers.equalTo("WorkStatus is invalid.")));

        // Case 2-3: range date between startingDate and endingDate (startingDate <= endingDate)
        work.setStartingDate(Date.valueOf("2020-01-10"));
        work.setEndingDate(Date.valueOf("2020-01-09"));
        json = mapper.writeValueAsString(work);
        Mockito.when(workService.save(ArgumentMatchers.any())).thenReturn(work);
        mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", Matchers.equalTo(ErrorCode.ARGUMENT_NOT_INVALID_CODE)))
                .andExpect(jsonPath("$.errors.workName", Matchers.equalTo("WorkName must be requid.")))
                .andExpect(jsonPath("$.errors.status", Matchers.equalTo("WorkStatus is invalid.")))
                .andExpect(jsonPath("$.errors.work", Matchers.equalTo("Invalid range between statingDate and endingDate.")));
    }

    /**
     * Test API update Work
     *
     * @throws Exception
     */
    @Test
    public void testUpdateWork() throws Exception {
        // Setting data for work
        Work work = new Work();
        work.setWorkName("Work_01");
        work.setStartingDate(Date.valueOf("2020-01-10"));
        work.setEndingDate(Date.valueOf("2020-01-11"));
        work.setStatus("Planning");

        Mockito.when(workService.update(ArgumentMatchers.any())).thenReturn(work);
        String json = mapper.writeValueAsString(work);

        // Case 1: fail
        mockMvc.perform(put(API_URL).contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8)
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", Matchers.equalTo(ErrorCode.FIND_NOT_FOUND_CODE)))
                .andExpect(jsonPath("$.message", Matchers.equalTo("Work is not found with WorkName 'Work_01'")));
    }

    /**
     * Test API delete Work
     *
     * @throws Exception
     */
    @Test
    public void testDeleteWork() throws Exception {
        Mockito.when(workService.deleteByName("Work_01")).thenReturn(true);
        // Case 1: success
        MvcResult requestResult = mockMvc.perform(delete(API_URL + "Work_01"))
                .andExpect(status().isAccepted())
                .andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Delete work success.");

        // Case 2: fail
        requestResult = mockMvc.perform(delete(API_URL + "Work_02"))
                .andExpect(status().isBadRequest())
                .andReturn();
        result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Delete work fail.");
    }
}