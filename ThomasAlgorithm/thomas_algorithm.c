#include <stdio.h>
#include <stdlib.h>

int main() {
    int n;
    printf("Введите размер матрицы: ");
    scanf("%d", &n);

    double **A = (double **)malloc(n * sizeof(double *));
    for (int i = 0; i < n; i++) {
        A[i] = (double *)malloc(n * sizeof(double));
    }

    double *b = (double *)malloc((n - 1) * sizeof(double));
    double *c = (double *)malloc(n * sizeof(double));
    double *a = (double *)malloc(n * sizeof(double));
    double *alpha = (double *)malloc(n * sizeof(double));
    double *beta = (double *)malloc(n * sizeof(double));
    double *f = (double *)malloc(n * sizeof(double));
    double *x = (double *)malloc(n * sizeof(double));

    printf("Введите матрицу А:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            scanf("%lf", &A[i][j]);
            if (i == j) c[i] = A[i][j];
            if ((i + 1) == j) b[i] = (-1) * A[i][j];
            if ((j + 1) == i) a[i] = (-1) * A[i][j];
        }
    }

    printf("Введите значения f:\n");
    for (int i = 0; i < n; i++) {
        scanf("%lf", &f[i]);
    }

    alpha[0] = b[0] / c[0];
    beta[0] = f[0] / c[0];

    for (int i = 1; i < n; i++) {
        if (i != (n - 1)) alpha[i] = b[i] / (c[i] - a[i] * alpha[i - 1]);
        beta[i] = (f[i] + a[i] * beta[i - 1]) / (c[i] - a[i] * alpha[i - 1]);
    }

    x[n - 1] = beta[n - 1];
    for (int i = n - 2; i >= 0; i--) {
        x[i] = alpha[i] * x[i + 1] + beta[i];
    }

    printf("Результат:\n");
    for (int i = 0; i < n; i++) {
        printf("%lf\n", x[i]);
    }

    for (int i = 0; i < n; i++) {
        free(A[i]);
    }
    free(A);
    free(b);
    free(c);
    free(a);
    free(alpha);
    free(beta);
    free(f);
    free(x);

    return 0;
}
