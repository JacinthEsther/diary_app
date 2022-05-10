package com.technophile.diaryapp.mockTests;


import com.technophile.diaryapp.controllers.DiaryDTO;
import com.technophile.diaryapp.controllers.requests.UpdateDiaryForm;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.Entry;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.DiaryRepository;
import com.technophile.diaryapp.repositories.UserRepository;
import com.technophile.diaryapp.services.DiaryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiaryServiceMockTest {

    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DiaryServiceImpl diaryService = new DiaryServiceImpl();

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    ArgumentCaptor<Diary> diaryArgumentCaptor;

    @Test
    void testThatCanCreateDiary(){
        User user = new User("Lord_Otunba_X@gmail.com", "Jesus1234");
        Diary diary = new Diary("My Secret Lovers and their Exes");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        Diary returnedDiary = diaryService.createDiary("My Secret Lovers and their Exes", "user id");
        verify(userRepository, times(1)).findById("user id");
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(returnedDiary.getTitle()).isEqualTo("My Secret Lovers and their Exes");
        assertThat(returnedDiary.getEntries()).isEmpty();
        assertThat(capturedUser.getDiaries()).hasSize(1);
        assertThat(new ArrayList<>(capturedUser.getDiaries()).get(0).getTitle()).isEqualTo("My Secret Lovers and their Exes");
    }

    @Test
    void testThatCanUpdateADiaryTitle(){
        Diary diary = new Diary("dummy id", "diary title");
        UpdateDiaryForm updateDiaryForm = new UpdateDiaryForm("new diary title");
        when(diaryRepository.findById(anyString())).thenReturn(Optional.of(diary));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        String expected = diaryService.updateDiary("dummy id", updateDiaryForm);
        verify(diaryRepository, times(1)).save(diaryArgumentCaptor.capture());
        Diary capturedDiary = diaryArgumentCaptor.getValue();
        assertThat(capturedDiary.getTitle()).isEqualTo("new diary title");
        assertThat(expected).isEqualTo("Diary has been updated");
    }

    @Test
    void testThatCanGetDiaryInformation(){
        Diary diary = new Diary("dummy id", "diary title");
        given(diaryRepository.findById("dummy id")).willReturn(Optional.of(diary));

       DiaryDTO diaryDTO= diaryService.getDiaryBy("dummy id");
//        then(diaryRepository).should().count();
        assertThat(diaryDTO).isNotNull();
    }

    @Test
    void testThatCanAddEntriesToDiary(){

        List<Entry> entries = List.of(
                new Entry("entry one"),
                new Entry("entry two")
        );
        Diary diary = new Diary("diaryId", "Otunba's Will");
        when(diaryRepository.findById(anyString())).thenReturn(Optional.of(diary));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        Diary updatedDiary = diaryService.addEntries(entries, diary.getId());
        verify(diaryRepository, times(1)).save(diaryArgumentCaptor.capture());
        Diary capturedDiary = diaryArgumentCaptor.getValue();
        assertThat(capturedDiary.getEntries()).containsAll(entries);
        assertThat(updatedDiary.getTitle()).isEqualTo("Otunba's Will");
    }
}
