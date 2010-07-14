function [ x, m ] = solvePCG( A, b, x0, tol, maxit, ptype )
%SOLVEPCG sovles a system of linear equations by using a preconditioned
% congjugate gradient method
    
    switch ptype 
        case 1
            % Jacobi
            % B^-1 = D
            % L= Id
            % U= D
            U = diag(diag(A));
            L = eye(size(A,1));
        case 2
            % SGS
            % B^-1 = (L+D)*(D)^-1*(D+U)
            [L,U] = lu(tril(A)*(triu(A)*diag(1./diag(A))));
        case 3
            % SSOR
            % B^-1 = 1/(2-w)*(1/w*D+L)*w*D^-1)*(1/w*D+U)
            w= 1.8;
            [L,U] = lu(1/(2-w)*((1/w*diag(diag(A))+tril(A,-1))*(w*diag(1./diag(A)))*(1/w*diag(diag(A))+triu(A,1)))); 
        otherwise
            L = eye(size(A,1));
            U = L;
    end
    % value for stop criterion
    b_norm_tol = norm(b) * tol;
    
    x_crnt = x0;
    % initial residual
    r_crnt = b - A * x_crnt;
    z_temp = L\r_crnt;
    z_crnt = U\z_temp;
    % initial conjugate direction
    p_crnt = z_crnt;
    r_z_crnt = dot(r_crnt, z_crnt);
    
    m=0;

    for k = 1:maxit  
        % if r_z_crnt == 0 then we have found the exact solution
        if (r_z_crnt ==0 || norm(r_crnt) < b_norm_tol)
            x = x_crnt;
            return
        end
        
        m = m+1;
        
        A_p_crnt = A * p_crnt;
        
        %ld minimizes F(x_crnt + ld*p_crnt) with F(x) = 1/2*x'*A*x-b'*x
        ld = r_z_crnt / dot(p_crnt, A_p_crnt); 
        x_next = x_crnt + ld * p_crnt;
        r_next = r_crnt - ld * A_p_crnt;

        z_temp = L\r_next;
        z_next = U\z_temp;
        r_z_next = dot(r_next, z_next);
        p_crnt = z_next + r_z_next / r_z_crnt * p_crnt;

        x_crnt = x_next;
        r_crnt = r_next;
        r_z_crnt = r_z_next;
    end
	exception = MException('solvePCG reached maxit iterations. The iteration probably does not converge');
	throw(exception);
end

