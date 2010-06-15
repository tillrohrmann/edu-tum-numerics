% qr_iteration calculates the eigenvalues of a matrix by using the qr-iteration
% T - matrix of which the eigenvalues shall be calculated
% s - mode switch (1 - Rayleigh quotient shift, 2 - Wilkinson shift)
% maxiter - specifies the maximum number of iterations within one deflation step
% TOL - coefficient for the stop criterion for one deflation step
% eigv - vector which stores the calculate eigenvalues
% subdiag - stores for each iteration step the subdiagonal of the Matrix T
% eigapprox - stores for each iteration step the diagonal of the Matrix T
function [eigv, subdiag, eigapprox] = qr_iteration(T,s,maxiter,TOL,eigv,subdiag,eigapprox)

%check contract
assert(size(T)(1)>0)
assert(size(T)(1)==size(T)(2))
assert(TOL >=0)

if(nargin > 5)
	errorInformation = true;
else
	errorInformation = false;
endif

rows = size(T)(1);

if(rows == 1)
	eigv = [T(1,1); eigv];
else
	%choose approximation method for eigenvalues
	if(s == 2)
		lambda = @wilkinson;
	else
		lambda = @rayleigh;
	endif
	
	for k = 1:maxiter
		lapprox = lambda(T);
		%TODO: I do not know whehter the fact, that the Matrix is in Hessenberg form will be exploited
		[Q,R] = qr(T-lapprox*eye(rows));
		T = R*Q+lapprox*eye(rows);
		
		if(errorInformation==true)	
			subdiag(:,end+1) =[diag(T,-1);zeros(size(eigv)(1),1)];
			eigapprox(:,end+1) =[diag(T); eigv];
		endif
		
		if(abs(T(rows,rows-1))<= TOL*(abs(T(rows-1,rows-1))+abs(T(rows,rows))))
			eigv = [T(rows,rows); eigv];
			if(errorInformation==true)
				[eigv,subdiag,eigapprox] = qr_iteration(T(1:rows-1,1:rows-1),s,maxiter,TOL,eigv,subdiag,eigapprox);
			else
				eigv = qr_iteration(T(1:rows-1,1:rows-1),s,maxiter,TOL,eigv);
			endif
			return
		endif
	endfor
	exception = MException('Iteration reached maxiter. The iteration probably does not converge');
	throw(exception);
endif 


% rayleigh calculates the eigenvalue approximation by returning the last element of the matrix A
% A - matrix 
% return lambda - eigenvalue approximation
function lambda = rayleigh(A)
	lambda = A(size(A)(1),size(A)(2));
end

% wilkinson calculates the eigenvalue approximation according to the wilkinson shift. It calculates
% the eigenvalues of the submatrix A(size(A)(1)-1:,size(A)(2)-1:) and chooses the eigenvalue which
% is closer to A(size(A)(1),size(A)(2))
% A - matrix
% return lambda - eigenvalue approximation
function lambda = wilkinson(A)
	s = A(size(A)(1)-1:size(A)(1),size(A)(2)-1:size(A)(2));
	root = sqrt((s(1,1)-s(2,2))^2+4*s(1,2)*s(2,1))/2;
	a = (s(1,1)+s(2,2))/2;
	l1 = a+root;
	l2 = a-root;
	if(abs(s(2,2)-l1) < abs(s(2,2)-l2))
		lambda = l1;
	else
		lambda = l2;
	endif
end

end