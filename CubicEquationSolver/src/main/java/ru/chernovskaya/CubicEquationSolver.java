package ru.chernovskaya;

import java.util.ArrayList;
import java.util.List;

public class CubicEquationSolver {

    // максимальная глубина рекурсии
    private final static int MAX_DEEP = 4000;

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Находит дискриминант выражения вида  3х^2+2ax+b.
     *
     * @param a значение перед х
     * @param b свободный член
     * @return значение дискриминанта
     */
    private double countDiscriminant(double a, double b) {
        return Math.pow(a, 2) - 3 * b;
    }

    /**
     * Подставляет параметр в уравнение
     *
     * @param x подставляемое значение
     * @param a знавение перед х^2
     * @param b знавение перед х
     * @param c свободный член
     * @return вычисленное значение
     */
    public static double f(double x, double a, double b, double c) {
        return Math.pow(x, 3) + a * Math.pow(x, 2) + b * x + c;
    }

    /**
     * Определяет количество корней квадратного уравнения вида 3х^2+2ax+b.
     *
     * @param discriminant  дискриминант
     * @param a             значение перед х в квадратном уравнении
     * @param eps           значение точности
     * @return              количество корней
     */
    private int rootCountDeterminer(double discriminant, double a, double eps) {
        if (discriminant < -4 * a * eps)
            return 0;
        else if (Math.abs(discriminant) <= 4 * a)
            return 1;
        else
            return 2;
    }

    /**
     * Находит два корня квадратного уравнения вида 3х^2+2ax+b.
     *
     * @param a             значение перед х в квадратном уравнении
     * @param discriminant  дискриминант
     * @return              массив из двух значений
     */
    private double[] countTwoRoot(double a, double discriminant) {
        double[] solvers = new double[2];

        solvers[0] = (-a - Math.sqrt(discriminant)) / 3;
        solvers[1] = (-a + Math.sqrt(discriminant)) / 3;

        return solvers;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Находит значение, при котором функция < eps.
     *
     * @param leftBound  левая граница
     * @param rightBound правая граница
     * @param a          значение перед х^2
     * @param b          значение перед х
     * @param c          свободный член
     * @param deep       глубина рекурсии
     * @param eps        значение точности
     * @return           значения точки в которой функция < eps
     */
    private double bisectionalRootSearch(double leftBound, double rightBound,
                                         double a, double b, double c, int deep, double eps) {
        if (Math.abs(f(leftBound, a, b, c)) <= eps)
            return leftBound;
        if (Math.abs(f(rightBound, a, b, c)) <= eps)
            return rightBound;

        double middle = (leftBound + rightBound) / 2;

        if (deep >= MAX_DEEP) return middle;

        double result = f(middle, a, b, c);

        if (Math.abs(result) <= eps) {
            return middle;
        } else if (result > eps) {
            if (f(leftBound, a, b, c) > eps)
                return bisectionalRootSearch(middle, rightBound, a, b, c, deep + 1, eps);
            else
                return bisectionalRootSearch(leftBound, middle, a, b, c, deep + 1, eps);
        } else {
            if (f(leftBound, a, b, c) > eps)
                return bisectionalRootSearch(leftBound, middle, a, b, c, deep + 1, eps);
            else
                return bisectionalRootSearch(middle, rightBound, a, b, c, deep + 1, eps);
        }
    }

    /**
     * Ищет корень на промежутке от минус бесконечности до меньшего корня.
     *
     * @param x_1        меньший корень уравнения
     * @param a          значение перед х^2
     * @param b          значение перед х
     * @param c          свободный член
     * @param delta      шаг
     * @param eps        значение точности
     * @return           корень изначального уравнения
     */
    private double searchRootFromInfinityToX_1(double x_1, double a, double b, double c, double delta, double eps) {
        double leftBound = x_1;
        int i = 0;
        while (f(leftBound, a, b, c) >= eps) {
            leftBound -= delta;
        }
        return bisectionalRootSearch(leftBound, x_1, a, b, c, 0, eps);
    }

    /**
     * Ищет корень на промежутке от большего корня до бесконечности.
     *
     * @param x_2        больший корень уравнения
     * @param a          значение перед х^2
     * @param b          значение перед х
     * @param c          свободный член
     * @param delta      шаг
     * @param eps        значение точности
     * @return           корень изначального уравнения
     */
    private double searchRootFromX_2ToInfinity(double x_2, double a, double b, double c, double delta, double eps) {
        double rightBound = x_2;
        int i = 0;
        while (f(rightBound, a, b, c) <= eps) {
            rightBound += delta;
        }
        return bisectionalRootSearch(x_2, rightBound, a, b, c, 0, eps);
    }
    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Поиск корней уравнения вида  3х^2+2ax+b = 0.
     *
     * @param a          значение перед х^2
     * @param b          значение перед х
     * @param c          свободный член
     * @param delta      шаг
     * @param eps        значение точности
     * @return           решения уравнения
     */
    private List<Double> solveEquation(double a, double b, double c, double delta, double eps) {
        List<Double> results = new ArrayList<Double>(3);

        double discriminant = countDiscriminant(a, b);
        int rootCount = rootCountDeterminer(discriminant, a, eps);

        if (rootCount == 0 || rootCount == 1) {

            double valueF0 = f(0, a, b, c);
            if (Math.abs(valueF0) <= eps) {
                results.add(0.0);
            } else if (valueF0 > eps) {
                results.add(searchRootFromInfinityToX_1(0, a, b, c, delta, eps));
            } else {
                results.add(searchRootFromX_2ToInfinity(0, a, b, c, delta, eps));
            }

        } else {
            double[] roots = countTwoRoot(a, discriminant);
            double x_1 = roots[0];
            double x_2 = roots[1];

            double valueFx_1 = f(x_1, a, b, c);
            double valueFx_2 = f(x_2, a, b, c);

            if (valueFx_1 > eps && valueFx_2 > eps) {
                results.add(searchRootFromInfinityToX_1(x_1, a, b, c, delta, eps));

            } else if (Math.abs(valueFx_2) <= eps) {
                results.add(x_2);
                results.add(searchRootFromInfinityToX_1(x_1, a, b, c, delta, eps));

            } else if (valueFx_1 > eps && valueFx_2 < -eps) {
                results.add(searchRootFromInfinityToX_1(x_1, a, b, c, delta, eps));
                results.add(bisectionalRootSearch(x_1, x_2, a, b, c, 0, eps));
                results.add(searchRootFromX_2ToInfinity(x_2, a, b, c, delta, eps));
            } else if (Math.abs(valueFx_1) <= eps) {
                results.add(x_1);
                results.add(searchRootFromX_2ToInfinity(x_2, a, b, c, delta, eps));
            } else if (valueFx_1 < -eps) {
                results.add(searchRootFromX_2ToInfinity(x_2, a, b, c, delta, eps));
            } else {
                results.add((x_1 + x_2) / 2);
            }
        }

        return results;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    public List<Double>  start(double a, double b, double c, double delta, double eps) {

        List<Double> results = solveEquation(a, b, c, delta, eps);

        return results;
    }
}
