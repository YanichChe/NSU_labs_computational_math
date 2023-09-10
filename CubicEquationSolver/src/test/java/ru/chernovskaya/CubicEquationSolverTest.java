package ru.chernovskaya;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class CubicEquationSolverTest {

    private final CubicEquationSolver cubicEquationSolver = new CubicEquationSolver();
    private TestInfo testInfo;

    @BeforeEach
    void init(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    @ParameterizedTest
    @MethodSource ("generateTest1IncorrectArgs")
    void test1(double a, double b, double c, double delta) {

        System.out.println(testInfo.getDisplayName());
        List<Double> results = cubicEquationSolver.start(a, b, c, delta, Math.pow(10, -5));

        System.out.println();
        for (double x : results) {
            System.out.printf("%-20s %-20s\n", String.format("x = %.7f ", x),
                    String.format("y  = %.7f", CubicEquationSolver.f(x, a, b, c)));
            Assertions.assertTrue(Math.abs(CubicEquationSolver.f(x, a, b, c)) <=  Math.pow(10, -5));
        }
        System.out.println("########################################################");
    }

    @ParameterizedTest
    @MethodSource ("generateTest2IncorrectArgs")
    void test2(double a, double b, double c, double delta, double eps) {
        System.out.println(testInfo.getDisplayName());
        List<Double> results = cubicEquationSolver.start(a, b, c, delta, eps);
        System.out.println();
        for (double x : results) {
            System.out.printf("%-20s %-20s\n", String.format("x = %.7f ", x),
                    String.format("y  = %.7f", CubicEquationSolver.f(x, a, b, c)));
            Assertions.assertTrue(Math.abs(CubicEquationSolver.f(x, a, b, c)) <=  eps);
        }
        System.out.println("########################################################");
    }

    private static Stream<Arguments> generateTest1IncorrectArgs() {
        return Stream.of(
                //Arguments.of(10865.01,	-1076608.65,	10765,	    1),
                Arguments.of(103.27,	326.901,	    -9.9,	    7),
                Arguments.of(10664.99,	-1076606.65,	10765,	    9),
                Arguments.of(-103.33,	333.099,	    -9.9,	    7),
                Arguments.of(10010,	    100025,	        250000,	    7.77),
                Arguments.of(3319,	    -46613,	        163317,	    7.77),
                Arguments.of(-10010,	100025,	        -250000,	1.111),
                Arguments.of(-99997.78,	-221998.7679,	-123210,	101),
                Arguments.of(-111000,	0,	            0,	        101),
                Arguments.of(17.6,	    77.44,	        0,	        0.23),
                Arguments.of(-17.6, 	77.44,	        0,	        0.23),
                Arguments.of(29.7,	    294.03,	        970.299,	200),
                Arguments.of(-21.3,	    151.23,	        -357.911,	200),
                Arguments.of(0,	        12345,	        0,	        200),
                Arguments.of(-1.12,	    5,	            -5.6,	    1000),
                Arguments.of(1.12,	    5,	            5.6,	    1000),
                Arguments.of(0,	        0,	            0,	        1000)
        );
    }

    private static Stream<Arguments> generateTest2IncorrectArgs() {
        return Stream.of(
                Arguments.of(-0.000001,	1,	-0.000001,	1000, Math.pow(10, -7)),
                Arguments.of(-0.000001,	1,	-0.000001,	1000, Math.pow(10, -4)),
                Arguments.of(-0.000001,	1,	-0.000001,	1000, Math.pow(10, -1)),
                Arguments.of(-0.000001,	1,	-0.000001,	1000, 1),
                Arguments.of(0,	        -2,	0,	        3,    Math.pow(10, -7)),
                Arguments.of(0,         -2,	0,	        3,    Math.pow(10, -4)),
                Arguments.of(0,	        -2,	0,	        3,    Math.pow(10, -1)),
                Arguments.of(0,	        -2,	0,	        3,    1)
        );
    }
}
