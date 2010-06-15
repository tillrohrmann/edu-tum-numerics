function gl_example()
% Aufgabe 2b)

n = 20;

figure(1);
set(gca,'FontSize',16);
xlabel('n');
ylabel('Maximaler Fehler der Knotenwerte');
grid;
hold on;
    
figure(2)
set(gca,'FontSize',16);
xlabel('n');
ylabel('Maximaler Fehler der Gewichte');
grid;
hold on;

for i=1:n
    [x,w] = gauss_legendre(i);
    [xexact,wexact] = gl(i);
    
    % ACHTUNG Die Reihenfolge der sortierten Quadraturpunkte stimmt NICHT
    % mit der Reihenfolge der Gewichte überein!!!!
    [x_sort,I] = sort(x);
    [xexact_sort,Iex] = sort(xexact);
    
   
    w_sort = w(I);
    wexact_sort = wexact(Iex);
    
    %keyboard;
    
    if i == 1
        xerr = norm(x_sort-xexact_sort,inf);
        werr = norm(w_sort-wexact_sort,inf);
    else
        xerr = [xerr norm(x_sort-xexact_sort,inf)];
        werr = [werr norm(w_sort-wexact_sort,inf)];
    end
    
end

figure(1);
plot(1:n,xerr,'bo-','LineWidth',2,'MarkerFaceColor','m');
    
figure(2)
plot(1:n,werr,'bo-','LineWidth',2,'MarkerFaceColor','m');
