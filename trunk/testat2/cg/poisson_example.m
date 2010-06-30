%%
%    Solve Ax = b, where A is the Poisson matrix and b = (1,...,1)T.
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear all; close all;

%%
col = {'b*-','go-','r+-','cd--'};

Nmax = 5;
N = 4;

for ii=1:4
    m = zeros(Nmax,1);
    N = 4;
    for j=1:Nmax
        A = gallery('poisson',N);
        b = ones(N*N,1);
        x0 = zeros(N*N,1);
        [x,m(j)] = solvePCG(A,b,x0,1e-3,10000,ii);
        N = 2*N;
    end
    plot(1:Nmax,m,col{ii},'LineWidth',3);
    grid;
    hold on;
end

legend('Jacobi','SGS','SSOR', 'NONE', 'Location', 'Best');

hold off;