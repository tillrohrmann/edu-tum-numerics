function quad_example()
% Aufgabe 3b)

% maximal number of gauss points
n = 20;

%% Aufgabe 3b)i)

% set function and interval
f = @(x)func1(x);
a = -5;
b = 5;

% compute exact solution
Sexact = atan(5.0)-atan(-5.0);

% perform quadrature
S = zeros(1,n);
for i=1:n
    S(i) = gauss_legendre_quadrature(f,a,b,i);
end

% plot error
figure(1);
semilogy(1:n,abs(S-Sexact),'bo-','LineWidth',2,'MarkerFaceColor','m');
set(gca,'FontSize',16);
xlabel('n');
ylabel('error');
grid;

%% Aufgabe 3b)ii)

% set function and interval
f = @(x)func2(x);
a = 0;
b = 1;

% compute exact solution
Sexact = 2/3; 

% perform quadrature
S = zeros(1,n);
for i=1:n
    S(i) = gauss_legendre_quadrature(f,a,b,i);
end

% plot error
figure(2);
semilogy(1:n,abs(S-Sexact),'bo-','LineWidth',2,'MarkerFaceColor','m');
set(gca,'FontSize',16);
xlabel('n');
ylabel('error');
grid;

