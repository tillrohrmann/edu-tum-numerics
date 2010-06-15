function qr_example()
% Aufgabe 1b)

% assemble the matrix A
A = sparse([2 3 4 5 6; 4 4 5 6 7; 0 3 6 7 8; 0 0 2 8 9; 0 0 0 1 10]);

% compute the eigenvalues of A
[eigv,subdiag,eigapprox] = qr_iteration(A,1,1000,1.0e-10,[],[],[]);

% sort the eigenvalues
eigapprox_sort = sort(eigapprox);

% compute the exact eigenvalues
exact_eig = eigs(A);
exact = sort(exact_eig);

% compute the error
err = sparse(size(A,1),length(eigapprox_sort));
for j = 1:length(eigapprox_sort)
    err(:,j) = abs(eigapprox_sort(:,j) - exact(:));
end

% plot subdiag 
figure(1);
semilogy(abs(subdiag(1,:)),'ro-','LineWidth',2,'MarkerFaceColor','m');
set(gca,'FontSize',16);
xlabel('Step i');
ylabel('Subdiagonaleintrag');
grid;
hold on;
semilogy(abs(subdiag(2,:)),'go-','LineWidth',2,'MarkerFaceColor','m');
semilogy(abs(subdiag(3,:)),'bo-','LineWidth',2,'MarkerFaceColor','m');
semilogy(abs(subdiag(4,:)),'co-','LineWidth',2,'MarkerFaceColor','m');
legend('|a^k_{2,1}|','|a^k_{3,2}|','|a^k_{4,3}|','|a^k_{5,4}|');
hold off;

% plot eigapprox
figure(2);
semilogy(abs(err(1,:)),'ro-','LineWidth',2,'MarkerFaceColor','m');
set(gca,'FontSize',16);
xlabel('Step i');
ylabel('Diagonaleintrag');
grid;
hold on;
semilogy(abs(err(2,:)),'go-','LineWidth',2,'MarkerFaceColor','m');
semilogy(abs(err(3,:)),'bo-','LineWidth',2,'MarkerFaceColor','m');
semilogy(abs(err(4,:)),'co-','LineWidth',2,'MarkerFaceColor','m');
semilogy(abs(err(5,:)),'mo-','LineWidth',2,'MarkerFaceColor','m');
legend('\lambda_1','\lambda_2','\lambda_3','\lambda_4','\lambda_5');
hold off;
