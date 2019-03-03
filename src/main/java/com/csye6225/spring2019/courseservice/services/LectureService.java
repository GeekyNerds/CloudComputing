package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2019.courseservice.datamodels.InMemoryDatabase;
import com.csye6225.spring2019.courseservice.datamodels.Lecture;

public class LectureService {
private HashMap<Long, Lecture> lectureMap = InMemoryDatabase.getLectureDB();
	
	public Lecture addLecture(Lecture lecture) {
		long nextAvailableId = lectureMap.size() + 1;
		lecture.setLectureId(nextAvailableId);
		lectureMap.put(nextAvailableId, lecture);
		return lectureMap.get(nextAvailableId);
	}
	
	public Lecture getLecture(long lectureId) {
		return lectureMap.get(lectureId);
	}
	
	public List<Lecture> getAllLectures() {
		return new ArrayList<Lecture>(lectureMap.values());
	}
	
	public Lecture deleteLecture(long lectureId) {
		return lectureMap.remove(lectureId);
	}
	
	public Lecture updateLecture(long lectureId, Lecture lecture) {
		Lecture lectureToBeUpdated = lectureMap.get(lectureId);
		String note = lecture.getNote();
		String material = lecture.getMaterial();
		
		if(note != null && !note.isEmpty()) {
			lectureToBeUpdated.setNote(note);
		}
		if(material != null && !material.isEmpty()) {
			lectureToBeUpdated.setMaterial(material);
		}
		
		return lectureToBeUpdated;
	}
	
	public List<Lecture> getLecturesByOptions(String courseId) {
		List<Lecture> lectureList = getAllLectures();
		if(courseId != null && !courseId.isEmpty()) {
			lectureList = getLecturesByCourseId(lectureList, courseId);
		}
		
		return lectureList;
	}
	
	public List<Lecture> getLecturesByCourseId(List<Lecture> lectureList, String courseId) {
		for(Lecture lecture : lectureMap.values()) {
			if(!lecture.getCourseId().equals(courseId)){
				lectureList.remove(lecture);
			}
		}
		
		return lectureList;
	}
	
}