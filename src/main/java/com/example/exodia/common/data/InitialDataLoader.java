package com.example.exodia.common.data;

import com.example.exodia.car.domain.Car;
import com.example.exodia.car.repository.CarRepository;
import com.example.exodia.department.domain.Department;
import com.example.exodia.evalutionFrame.evalutionBig.domain.Evalutionb;
import com.example.exodia.evalutionFrame.evalutionBig.repository.EvalutionbRepository;
import com.example.exodia.evalutionFrame.evalutionMiddle.domain.Evalutionm;
import com.example.exodia.evalutionFrame.evalutionMiddle.repository.EvalutionmRepository;
import com.example.exodia.meetingRoom.domain.MeetingRoom;
import com.example.exodia.meetingRoom.repository.MeetingRoomRepository;
import com.example.exodia.position.domain.Position;
import com.example.exodia.user.domain.User;
import com.example.exodia.user.domain.Gender;
import com.example.exodia.user.domain.Status;
import com.example.exodia.user.domain.HireType;
import com.example.exodia.user.domain.NowStatus;
import com.example.exodia.user.repository.UserRepository;
import com.example.exodia.department.repository.DepartmentRepository;
import com.example.exodia.position.repository.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final EvalutionbRepository evalutionbRepository;
    private final EvalutionmRepository evalutionmRepository;
    private final PasswordEncoder passwordEncoder;
    private final MeetingRoomRepository meetingRoomRepository;
    private final CarRepository carRepository;

    public InitialDataLoader(DepartmentRepository departmentRepository,
                             PositionRepository positionRepository,
                             UserRepository userRepository,
                             EvalutionbRepository evalutionbRepository,
                             EvalutionmRepository evalutionmRepository,
                             PasswordEncoder passwordEncoder,
                             MeetingRoomRepository meetingRoomRepository, CarRepository carRepository) {
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
        this.evalutionbRepository = evalutionbRepository;
        this.evalutionmRepository = evalutionmRepository;
        this.passwordEncoder = passwordEncoder;
        this.meetingRoomRepository = meetingRoomRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 본부 생성
        Department salesHQ = new Department("영업본부", null);
        Department devHQ = new Department("개발본부", null);
        Department supportHQ = new Department("지원본부", null);
        departmentRepository.save(salesHQ);
        departmentRepository.save(devHQ);
        departmentRepository.save(supportHQ);

        // 지원본부 밑에 인사팀 추가
        Department hrDepartment = new Department("인사팀", supportHQ);
        departmentRepository.save(hrDepartment);

        // 경영지원부서 밑에 경영1팀 추가
        Department managementDepartment = new Department("경영지원부서", supportHQ);
        departmentRepository.save(managementDepartment);

        Department managementTeam1 = new Department("경영1팀", managementDepartment);
        departmentRepository.save(managementTeam1);

        // 직위 추가
        Position teamLeader = new Position(null, "팀장");
        Position director = new Position(null, "부장");
        Position assistantManager = new Position(null, "대리");
        positionRepository.save(teamLeader);
        positionRepository.save(director);
        positionRepository.save(assistantManager);

        // 사용자 추가
        String password1 = passwordEncoder.encode("testtest");
        String password2 = passwordEncoder.encode("testtest");
        String password3 = passwordEncoder.encode("testtest");

        User user1 = new User(
                null,
                "20240901001",
                null,
                "이예나",
                Gender.M,
                Status.재직,
                password1,
                "test1@test",
                "어양동",
                "01012345678",
                "123456-1234567",
                HireType.정규직,
                NowStatus.출근,
                15,
                hrDepartment,
                teamLeader,
                0
        );

        User user2 = new User(
                null,
                "20240901002",
                null,
                "김수연",
                Gender.W,
                Status.재직,
                password2,
                "test2@test",
                "영등동",
                "01098765432",
                "123456-2345678",
                HireType.계약직,
                NowStatus.회의,
                15,
                hrDepartment,
                director,
                0
        );

        // 경영1팀 소속 user 추가
        User user3 = new User(
                null,
                "20240901003",
                null,
                "test3",
                Gender.W,
                Status.재직,
                password3,
                "test3@test",
                "신촌동",
                "01033333333",
                "123456-3456789",
                HireType.정규직,
                NowStatus.회의,
                10,
                managementTeam1,
                assistantManager,
                0
        );

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // 대분류 데이터 생성
        Evalutionb workAbility = new Evalutionb(null, "업무 수행 능력");
        Evalutionb problemSolving = new Evalutionb(null, "문제 해결");
        Evalutionb responsibility = new Evalutionb(null, "책임감");
        Evalutionb teamworkCommunication = new Evalutionb(null, "팀워크/의사소통");

        evalutionbRepository.save(workAbility);
        evalutionbRepository.save(problemSolving);
        evalutionbRepository.save(responsibility);
        evalutionbRepository.save(teamworkCommunication);

        // 중분류 데이터 생성 및 대분류와 연결
        Evalutionm workAchievement = new Evalutionm(null, "업무달성도", workAbility);
        Evalutionm workProcessingAbility = new Evalutionm(null, "업무처리능력", workAbility);

        Evalutionm problemSolvingAbility = new Evalutionm(null, "문제 해결 능력", problemSolving);
        Evalutionm initiative = new Evalutionm(null, "주도성", problemSolving);

        Evalutionm cooperativeAttitude = new Evalutionm(null, "협력태도", responsibility);

        Evalutionm teamwork = new Evalutionm(null, "팀워크", teamworkCommunication);
        Evalutionm communication = new Evalutionm(null, "의사소통", teamworkCommunication);

        evalutionmRepository.save(workAchievement);
        evalutionmRepository.save(workProcessingAbility);
        evalutionmRepository.save(problemSolvingAbility);
        evalutionmRepository.save(initiative);
        evalutionmRepository.save(cooperativeAttitude);
        evalutionmRepository.save(teamwork);
        evalutionmRepository.save(communication);

        /* 회의실 기본 제공 데이터 */
        List<MeetingRoom> initialMeetingRooms = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            MeetingRoom meetingRoom = MeetingRoom.builder()
                    .name("회의실 " + i)
                    .build();
            initialMeetingRooms.add(meetingRoom);
        }
        meetingRoomRepository.saveAll(initialMeetingRooms);

        List<Car> cars = new ArrayList<>();
        cars.add(new Car(null, "121가123", "스타랙스"));
        cars.add(new Car(null, "135나894", "밴츠"));
        cars.add(new Car(null, "753호159", "SUV"));
        cars.add(new Car(null, "143라3451", "람보르기니"));
        cars.add(new Car(null, "429호7318", "G70"));
        cars.add(new Car(null, "14라 8222", "소나타"));
        cars.add(new Car(null, "18유3752", "황금마티즈"));
        carRepository.saveAll(cars);
    }
}
